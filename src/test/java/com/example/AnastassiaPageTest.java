package com.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AnastassiaPageTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://anastassiakostjuk24.thkit.ee/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void toggleMenu_hideAndShow() {
        WebElement menu = driver.findElement(By.id("hideseek"));
        WebElement toggleButton = driver.findElement(By.id("toggle"));

        toggleButton.click();
        wait.until(ExpectedConditions.invisibilityOf(menu));
        assertFalse(menu.isDisplayed(), "Menu should be hidden");

        toggleButton.click();
        wait.until(ExpectedConditions.visibilityOf(menu));
        assertTrue(menu.isDisplayed(), "Menu should be visible again");
    }

    @Test
    void panel_slidesDownAndUp() {
        WebElement flipDiv = driver.findElement(By.id("flip"));
        WebElement panel = driver.findElement(By.id("panel"));

        boolean initialVisible = panel.isDisplayed();

        flipDiv.click();
        wait.until(driver -> panel.isDisplayed() != initialVisible);
        assertNotEquals(initialVisible, panel.isDisplayed());

        flipDiv.click();
        wait.until(driver -> panel.isDisplayed() == initialVisible);
        assertEquals(initialVisible, panel.isDisplayed());
    }

    @Test
    void animateButton_triggersAnimation() {
        // Создаем WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Находим элементы на странице
        WebElement aurora = driver.findElement(By.id("aurora"));
        WebElement animateButton = driver.findElement(By.id("animateButton"));

        // Проверяем, что aurora изначально скрыта
        assertFalse(aurora.isDisplayed(), "Aurora should be hidden initially");

        // Ждем, пока кнопка станет кликабельной и кликаем
        wait.until(ExpectedConditions.elementToBeClickable(animateButton)).click();

        // Ждем, пока aurora станет видимой после клика
        wait.until(ExpectedConditions.visibilityOf(aurora));

        // Проверяем, что aurora теперь видна
        assertTrue(aurora.isDisplayed(), "Aurora should appear after click");
    }

    @Test
    void gifs_toggleVisibility() {
        WebElement pilt = driver.findElement(By.id("pilt"));
        WebElement toggleButton = driver.findElement(By.id("toggle"));

        boolean initial = pilt.isDisplayed();

        toggleButton.click();
        wait.until(driver -> pilt.isDisplayed() != initial);
        assertNotEquals(initial, pilt.isDisplayed());
    }

    @Test
    void h2_changesColorOnHover() {
        WebElement heading = driver.findElement(By.tagName("h2"));

        // Получаем исходный цвет
        String initialColor = heading.getCssValue("color");

        // Наводим курсор на элемент
        new org.openqa.selenium.interactions.Actions(driver)
                .moveToElement(heading)
                .perform();

        // Ждем, пока цвет изменится на белый
        wait.until(d -> {
            String color = heading.getCssValue("color");
            return !color.equals(initialColor);
        });

        String hoveredColor = heading.getCssValue("color");
        assertNotEquals(initialColor, hoveredColor, "The colour should change when hovering over it.");

        // Уводим курсор с элемента
        new org.openqa.selenium.interactions.Actions(driver)
                .moveByOffset(200, 200)
                .perform();

        // Ждем, пока цвет вернется
        wait.until(d -> heading.getCssValue("color").equals(initialColor));

        String finalColor = heading.getCssValue("color");
        assertEquals(initialColor, finalColor, "The colour should return after the cursor moves away.");
    }

}
