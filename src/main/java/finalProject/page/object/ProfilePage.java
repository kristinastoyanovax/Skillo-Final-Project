package finalProject.page.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
        WebElement clickNewPost = wait.until(ExpectedConditions.elementToBeClickable(By.tagName("app-post")));
        clickNewPost.click();
    }

    public int getPostCount(String status) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement allPostStatusFilter = wait.until(ExpectedConditions.elementToBeClickable(By.className("post-filter-buttons")));


        var statusFilters = allPostStatusFilter.findElements(By.tagName("label"));
        var elementToClick = status.equals("public") ? statusFilters.get(1) : statusFilters.get(2);

        try {
            elementToClick.click();
            Thread.sleep(2000);
        } catch (Exception e) {}

        return driver.findElements(By.tagName("app-post")).size();
    }
    public void setNumberPosts(int postCount) {
        expectedNumberPosts = postCount + 1;
    }
}
