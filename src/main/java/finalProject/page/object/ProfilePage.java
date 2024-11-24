package finalProject.page.object;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfilePage {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/users/";
    private final WebDriver driver;
    public int expectedNumberPosts;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getUsername() {
        WebElement username = driver.findElement(By.tagName("h2"));
        return username.getText();
    }

    public boolean isUrlLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.urlContains(ProfilePage.PAGE_URL));
    }

    public void clickPost() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement allPostStatusFilter = wait.until(ExpectedConditions.elementToBeClickable(By.className("post-filter-buttons")));

        var statusFilters = allPostStatusFilter.findElements(By.tagName("label"));
        try {
            statusFilters.getFirst().click();
        }
        catch (Exception _){}
        WebElement clickNewPost = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("app-post")));
        clickNewPost.click();
    }

    public int getPostCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.tagName("app-post")));
        WebElement allPostStatusFilter = wait.until(ExpectedConditions.elementToBeClickable(By.className("post-filter-buttons")));
        List<WebElement> statusFilters = allPostStatusFilter.findElements(By.tagName("label"));
        try {
            statusFilters.getFirst().click();
        } catch (Exception _) {

        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.tagName("app-spinner")));

        int numberOfPosts = 0;
        try {
            numberOfPosts = driver.findElements(By.tagName("app-post")).size();
        } catch (TimeoutException _) {
            return 0;
        }
        return numberOfPosts;
    }
}
