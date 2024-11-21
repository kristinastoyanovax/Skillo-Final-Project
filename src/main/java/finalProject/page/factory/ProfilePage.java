package finalProject.page.factory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfilePage {
    public static final String PAGE_URL = "http://training.skillo-bg.com:4300/users/";
    private final WebDriver driver;
    public int expectedNumberPosts;
    @FindBy(tagName = "h2")
    private WebElement username;
    @FindBy(tagName = "app-post")
    private WebElement clickNewPost;
    @FindBy(className = "post-filter-buttons")
    private WebElement allPostStatusFilter;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getUsername() {
        return username.getText();
    }

    public boolean isUrlLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.urlContains(ProfilePage.PAGE_URL));
    }

    public void clickPost() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(clickNewPost));
        clickNewPost.click();
    }

    public int getPostCount(String status) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(allPostStatusFilter));


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
