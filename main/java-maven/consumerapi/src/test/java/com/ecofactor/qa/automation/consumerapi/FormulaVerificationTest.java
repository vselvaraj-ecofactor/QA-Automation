package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.*;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.EcpCoreEnergyPricing;
import com.ecofactor.common.pojo.EcpCoreLocation;
import com.ecofactor.common.pojo.HvacComponent;
import com.ecofactor.common.pojo.HvacModel;
import com.ecofactor.common.pojo.Location;
import com.ecofactor.common.pojo.Thermostat;
import com.ecofactor.common.pojo.ThermostatHvacStage;
import com.ecofactor.common.pojo.ThermostatHvacStage.StageType;
import com.ecofactor.qa.automation.consumerapi.data.ApiDataProvider;
import com.ecofactor.qa.automation.dao.LocationDao;
import com.ecofactor.qa.automation.dao.SavingsDaoModule;
import com.ecofactor.qa.automation.dao.ThermostatDao;
import com.ecofactor.qa.automation.dao.ecp.EcpCoreEnergyPricingDao;
import com.ecofactor.qa.automation.dao.ecp.EcpCoreLocationDao;
import com.ecofactor.qa.automation.dao.hvac.HvacComponentDao;
import com.ecofactor.qa.automation.dao.hvac.HvacModelDao;
import com.ecofactor.qa.automation.dao.hvac.ThermostatHvacStageDao;
import com.ecofactor.qa.automation.dao.quant.SavingsReportDao;
import com.ecofactor.qa.automation.platform.constants.Groups;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.pojo.EWQuantReport;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

@Guice(modules = { UtilModule.class, ApiModule.class, SavingsDaoModule.class })
public class FormulaVerificationTest extends AbstractTest {

    @Inject
    private ConsumerApiURL consumerApiURL;

    @Inject
    private HvacComponentDao hvacComponentDao;

    @Inject
    private HvacModelDao hvacModelDao;

    @Inject
    private ThermostatHvacStageDao thermostatHvacStageDao;

    @Inject
    private ThermostatDao thermostatDao;

    @Inject
    private EcpCoreLocationDao ecpCoreLocationDao;

    @Inject
    private EcpCoreEnergyPricingDao ecpCoreEnergyPricingDao;

    @Inject
    private SavingsReportDao savingsReportDao;

    @Inject
    private LocationDao locationDao;

    @Test(groups = { Groups.SANITY1 }, dataProvider = "dollarSavings", dataProviderClass = ApiDataProvider.class, priority = 1)
    public void dollarSavingsForHeatFurnace(final String username, final String password) {

        setLogString("Verify dollarSavings.", true);

        final List<Location> locations = locationDao.getLocationByUserName(username);
        for (final Location location : locations) {
            final List<Thermostat> tstats = thermostatDao.listThermostatsByLocation(location
                    .getId());

            final EcpCoreLocation ecpLocation = ecpCoreLocationDao.findById(location.getId());
            final EcpCoreEnergyPricing gasPricing = ecpCoreEnergyPricingDao.findByEcpCoreAndType(
                    ecpLocation.getEcpCore(), "GAS");
            final EcpCoreEnergyPricing electricityPricing = ecpCoreEnergyPricingDao
                    .findByEcpCoreAndType(ecpLocation.getEcpCore(), "ELECT");

            final Response response = consumerApiURL.getAccumulatedDollarSavings(
                    String.valueOf(location.getId()), securityCookie);
            final String content = response.readEntity(String.class);

            setLogString("JSON Response:", true, CustomLogLevel.MEDIUM);
            setLogString(content, true, CustomLogLevel.MEDIUM);

            final JSONObject jsonObject = JsonUtil.parseObject(content);
            final JSONObject months = (JSONObject) jsonObject.get("months");

            String[] dateFields = null;
            for (final Object entry : months.entrySet()) {
                final Map.Entry<String, JSONObject> entryMap = (Map.Entry<String, JSONObject>) entry;
                final String month = entryMap.getKey();
                setLogString("Month --> " + month, true, CustomLogLevel.MEDIUM);
                dateFields = month.split("-");
                
                final JSONObject monthData = (JSONObject) entryMap.getValue();
                final JSONObject data = (JSONObject) monthData.get("heat");
                final double dollarSavings = (Double) data.get("dollars_actual");
                
                double dollar = 0.0;
                for (final Thermostat tstat : tstats) {
                    final Calendar startDate = Calendar.getInstance();
                    startDate.clear();
                    startDate.set(Integer.parseInt(dateFields[0]),
                            Integer.parseInt(dateFields[1]) - 1, 1);
                    final Calendar endDate = Calendar.getInstance();
                    endDate.clear();
                    endDate.set(Integer.parseInt(dateFields[0]),
                            Integer.parseInt(dateFields[1]) - 1, 27);
                    final List<EWQuantReport> reports = savingsReportDao.listThermostatSavings(
                            tstat.getId(), startDate, endDate);

                    if(reports == null || reports.isEmpty() || reports.get(0).getRuntimeHoursHeat() == null) {
                        continue;
                    }

                    final double runtimeHours = reports.get(0).getRuntimeHoursHeat();
                    final List<ThermostatHvacStage> tstatStages = thermostatHvacStageDao
                            .findByTstatId(tstat.getId());
                    HvacModel model;
                    HvacComponent component;
                    for (final ThermostatHvacStage tstatStage : tstatStages) {

                        model = hvacModelDao.findById(tstatStage.getHvacModelId());
                        component = hvacComponentDao.findById(model.getHvacComponentId());

                        if (tstatStage.getType() == StageType.HEAT
                                && component.getType().equalsIgnoreCase("HEATER")) {

                            dollar += ((model.getInputBtuh() / 1027000 * gasPricing.getPrice()) + (electricityPricing
                                    .getPrice() * 0.5) * runtimeHours);
                            break;
                        }
                    }
                }

                setLogString("API value == " + dollarSavings + ", Calculated Value == " + dollar , true);
                Assert.assertTrue(dollarSavings == dollar);
            }
        }
    }
    
    @Test(groups = { Groups.SANITY1 }, dataProvider = "dollarSavings", dataProviderClass = ApiDataProvider.class, priority = 1)
    public void dollarSavingsForAC(final String username, final String password) {

        setLogString("Verify dollarSavings.", true);

        final List<Location> locations = locationDao.getLocationByUserName(username);
        for (final Location location : locations) {
            final List<Thermostat> tstats = thermostatDao.listThermostatsByLocation(location
                    .getId());

            final EcpCoreLocation ecpLocation = ecpCoreLocationDao.findById(location.getId());
            final EcpCoreEnergyPricing electricityPricing = ecpCoreEnergyPricingDao
                    .findByEcpCoreAndType(ecpLocation.getEcpCore(), "ELECT");

            final Response response = consumerApiURL.getAccumulatedDollarSavings(
                    String.valueOf(location.getId()), securityCookie);
            final String content = response.readEntity(String.class);

            setLogString("JSON Response:", true, CustomLogLevel.MEDIUM);
            setLogString(content, true, CustomLogLevel.MEDIUM);

            final JSONObject jsonObject = JsonUtil.parseObject(content);
            final JSONObject months = (JSONObject) jsonObject.get("months");

            String[] dateFields = null;
            for (final Object entry : months.entrySet()) {
                final Map.Entry<String, JSONObject> entryMap = (Map.Entry<String, JSONObject>) entry;
                final String month = entryMap.getKey();
                setLogString("Month --> " + month, true, CustomLogLevel.MEDIUM);
                dateFields = month.split("-");
                
                final JSONObject monthData = (JSONObject) entryMap.getValue();
                final JSONObject data = (JSONObject) monthData.get("cool");
                final double dollarSavings = (Double) data.get("dollars_actual");
                
                double dollar = 0.0;
                for (final Thermostat tstat : tstats) {
                    final Calendar startDate = Calendar.getInstance();
                    startDate.clear();
                    startDate.set(Integer.parseInt(dateFields[0]),
                            Integer.parseInt(dateFields[1]) - 1, 1);
                    final Calendar endDate = Calendar.getInstance();
                    endDate.clear();
                    endDate.set(Integer.parseInt(dateFields[0]),
                            Integer.parseInt(dateFields[1]) - 1, 27);
                    final List<EWQuantReport> reports = savingsReportDao.listThermostatSavings(
                            tstat.getId(), startDate, endDate);

                    if(reports == null || reports.isEmpty() || reports.get(0).getRuntimeHoursCool() == null) {
                        continue;
                    }

                    final double runtimeHours = reports.get(0).getRuntimeHoursCool();
                    final List<ThermostatHvacStage> tstatStages = thermostatHvacStageDao
                            .findByTstatId(tstat.getId());
                    HvacModel model;
                    HvacComponent component;
                    for (final ThermostatHvacStage tstatStage : tstatStages) {

                        model = hvacModelDao.findById(tstatStage.getHvacModelId());
                        component = hvacComponentDao.findById(model.getHvacComponentId());

                        if (tstatStage.getType() == StageType.COOL
                                && component.getType().equalsIgnoreCase("AC")) {

                            dollar += ((((model.getTonnage() * 12000) / (Double.valueOf(model.getSeer()) * 1000)) + 0.5) * electricityPricing.getPrice() * runtimeHours);
                            break;
                        }
                    }
                }

                setLogString("API value == " + dollarSavings + ", Calculated Value == " + dollar , true);
                Assert.assertTrue(dollarSavings == dollar);
            }
        }
    }    
    
    @Test(groups = { Groups.SANITY1 }, dataProvider = "dollarSavings", dataProviderClass = ApiDataProvider.class, priority = 1)
    public void dollarSavingsForHeatPump(final String username, final String password) {

        setLogString("Verify dollarSavings.", true);

        final List<Location> locations = locationDao.getLocationByUserName(username);
        for (final Location location : locations) {
            final List<Thermostat> tstats = thermostatDao.listThermostatsByLocation(location
                    .getId());

            final EcpCoreLocation ecpLocation = ecpCoreLocationDao.findById(location.getId());
            final EcpCoreEnergyPricing electricityPricing = ecpCoreEnergyPricingDao
                    .findByEcpCoreAndType(ecpLocation.getEcpCore(), "ELECT");

            final Response response = consumerApiURL.getAccumulatedDollarSavings(
                    String.valueOf(location.getId()), securityCookie);
            final String content = response.readEntity(String.class);

            setLogString("JSON Response:", true, CustomLogLevel.MEDIUM);
            setLogString(content, true, CustomLogLevel.MEDIUM);

            final JSONObject jsonObject = JsonUtil.parseObject(content);
            final JSONObject months = (JSONObject) jsonObject.get("months");

            String[] dateFields = null;
            for (final Object entry : months.entrySet()) {
                final Map.Entry<String, JSONObject> entryMap = (Map.Entry<String, JSONObject>) entry;
                final String month = entryMap.getKey();
                setLogString("Month --> " + month, true, CustomLogLevel.MEDIUM);
                dateFields = month.split("-");
                
                final JSONObject monthData = (JSONObject) entryMap.getValue();
                final JSONObject data = (JSONObject) monthData.get("heat");
                final double dollarSavings = (Double) data.get("dollars_actual");
                
                double dollar = 0.0;
                for (final Thermostat tstat : tstats) {
                    final Calendar startDate = Calendar.getInstance();
                    startDate.clear();
                    startDate.set(Integer.parseInt(dateFields[0]),
                            Integer.parseInt(dateFields[1]) - 1, 1);
                    final Calendar endDate = Calendar.getInstance();
                    endDate.clear();
                    endDate.set(Integer.parseInt(dateFields[0]),
                            Integer.parseInt(dateFields[1]) - 1, 27);
                    final List<EWQuantReport> reports = savingsReportDao.listThermostatSavings(
                            tstat.getId(), startDate, endDate);

                    if(reports == null || reports.isEmpty() || reports.get(0).getRuntimeHoursHeat() == null) {
                        continue;
                    }
                    
                    final double runtimeHours = reports.get(0).getRuntimeHoursHeat();
                    final List<ThermostatHvacStage> tstatStages = thermostatHvacStageDao
                            .findByTstatId(tstat.getId());
                    HvacModel model;
                    HvacComponent component;
                    for (final ThermostatHvacStage tstatStage : tstatStages) {

                        model = hvacModelDao.findById(tstatStage.getHvacModelId());
                        component = hvacComponentDao.findById(model.getHvacComponentId());

                        if (tstatStage.getType() == StageType.HEAT
                                && component.getType().equalsIgnoreCase("HP")) {

                            dollar += ((((model.getTonnage() * 12000) / (Double.valueOf(model.getHspf()) * 1000)) + 0.5) * electricityPricing.getPrice() * runtimeHours);
                            break;
                        }
                    }
                }

                setLogString("API value == " + dollarSavings + ", Calculated Value == " + dollar , true);
                Assert.assertTrue(dollarSavings == dollar);
            }
        }
    }
}
