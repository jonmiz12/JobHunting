package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class BaseTest {

    String userName = "יהונתן";
    String email = Utils.readProperty("email");
    String password = Utils.readProperty("password");
    Object agentNames [][] = {{"Automation Engineer"},{"מפתח/ת אוטומציה"},{"מהנדס/ת אוטומציה"},{"Automation Developer"}};
    String[] jobskeys = {"automation","אוטומציה","אוטומטיות","Automation"};

    WebDriver driver;

    @BeforeMethod
    public void setup(ITestContext testContext){
        try {
            Runtime.getRuntime().exec("taskkill.exe /F /IM chromedriver.exe /T" + "cmd.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(Utils.readProperty("drushimurl"));
    }

    @AfterMethod
    public void TearDown(){
        driver.quit();
    }

    public String[] removeArrayFromArray(String[] bigArr, String[] smallArr) {
        ArrayList<String> list = new ArrayList<String>();
        String[] arr = new String[list.size()];
        boolean remove;
        for (int i=0; i<bigArr.length; i++){
            for (int j=0; j<smallArr.length; j++){
                if (smallArr[j].equals(bigArr[i])) {
                    break;
                } else {
                    if (j==smallArr.length-1) {
                        list.add(bigArr[i]);
                    }
                }
            }
        }
        arr = list.toArray(arr);
        return arr;
    }

    public Double[] convertArrayToDouble(String[] sArr) {
        Double[] dArr = new Double[sArr.length];
        for (int i=0; i<sArr.length; i++) {
            dArr[i]=Double.parseDouble(sArr[i]);
        }
        return dArr;
    }

    public Double sumPrices(Double[] prices) {
        Double sum = 0.0;
        for (Double price : prices) {
            sum = sum + price;
        }
        return sum;
    }

    public Double[] removePartiallyRemoveArrayFromPrices(String[] itemNames, String[] updatedArray, Double[] prices) {
        ArrayList<Double> UpdatedPrices = new ArrayList<Double>();
        Double[] arr = new Double[updatedArray.length];
        for (int i=0; i<itemNames.length; i++) {
            for (int j=0; j<updatedArray.length; j++) {
                if (itemNames[i].equals(updatedArray[j])) {
                    UpdatedPrices.add(prices[i]);
                }
            }
        }
        arr = UpdatedPrices.toArray(arr);
        return arr;
    }
}
