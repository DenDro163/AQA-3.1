package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.By.cssSelector;

public class CallBackWebDriverManagerTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
//библиотека webdriver manager автоматически определяет ОС и версию браузера, скачивает и устанавливает подходящий файл драйвера
        WebDriverManager.chromedriver().setup();//Выбираем драйвер для нужного браузера и путь до него.
    }

    @BeforeEach
    void setUp() {
        //  Перед каждым тестом запускаем драйвер
//  запуск браузера в режиме headless - отключаем графический интерфейс
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);//
        driver.get("http://localhost:9999");
    }

    @AfterEach
//  завершение работы браузера после каждого теста
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldTestSendForm() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Бакланов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".form-field .button__content")).click();//кнопка продолжить
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=order-success]")).isEnabled());
    }

    @Test
    public void shouldTestWarningIfFormEmpty() {//
        driver.findElement(By.cssSelector(".form-field .button__content")).click();
        assertTrue(driver.findElement(By.cssSelector(".input_invalid")).isEnabled());
    }

    @Test
    public void shouldTestWarningIfNameFieldEmpty() {//
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+77777777777");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".form-field .button__content")).click();
        assertTrue(driver.findElement(By.cssSelector(".input_invalid")).isEnabled());
    }

    @Test
    public void shouldTestWarningIfTelephoneFieldEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василь Протонов");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".form-field .button__content")).click();
        assertTrue(driver.findElement(By.cssSelector(".input_invalid")).isEnabled());
    }

    @Test
    public void shouldTestWarningIfCheckboxFieldEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василь Протонов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+77777777777");
        driver.findElement(By.cssSelector(".form-field .button__content")).click();
        assertTrue(driver.findElement(By.cssSelector(".input_invalid")).isEnabled());
    }

    @Test
    public void shouldTestWarningIfBadName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Lol Shto");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+77777777777");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".form-field .button__content")).click();
        assertTrue(driver.findElement(By.cssSelector(".input_invalid")).isEnabled());
    }

    @Test
    public void shouldTestWarningIfBadTelephoneLessNumber() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василь Протонов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+12345");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".form-field .button__content")).click();
        assertTrue(driver.findElement(By.cssSelector(".input_invalid")).isEnabled());
    }

    @Test
    public void shouldTestWarningIfBadTelephoneMoreNumber() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василь Протонов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+723456789012");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".form-field .button__content")).click();
        assertTrue(driver.findElement(By.cssSelector(".input_invalid")).isEnabled());
    }

    @Test
    public void shouldTestWarningIfBadTelephoneLetterNumber() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василь Протонов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+afornfgcjsq");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".form-field .button__content")).click();
        assertTrue(driver.findElement(By.cssSelector(".input_invalid")).isEnabled());
    }
}
