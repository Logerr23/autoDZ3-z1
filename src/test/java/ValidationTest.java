import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {


    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");

    }
    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    void shouldGiveMessageAboutInvalidName(){
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Harry Potter");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+78005553535");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.cssSelector("[type = button]")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id = name] span.input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldGiveMessageAboutInvalidPhoneNumber(){
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Гарри Гончар");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+7 (800) 555 35 35");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.cssSelector("[type = button]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id = phone] span.input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldShowMessageAboutEmptyFieldName(){
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+78005553535");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.cssSelector("[type = button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id = name] span.input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldShowMessageAboutEmptyFieldPhoneNumber(){
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Гарри Гончар");
        driver.findElement(By.cssSelector("[data-test-id = agreement] ")).click();
        driver.findElement(By.cssSelector("[type = button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id = phone] span.input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldMakeTheCheckboxRed(){
        driver.findElement(By.cssSelector("[data-test-id = name] input")).sendKeys("Гарри Гончар");
        driver.findElement(By.cssSelector("[data-test-id = phone] input")).sendKeys("+78005553535");
        driver.findElement(By.cssSelector("[type = button]")).click();
        assertTrue(driver.findElement(By.cssSelector("label.input_invalid")).isDisplayed());

    }

}
