package finalProject.page.factory;

import org.openqa.selenium.*;
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
    @FindAll(@FindBy (tagName = "app-post"))
    private List<WebElement> posts;
    @FindBy(tagName = "app-spinner")
    private WebElement loadingSpinner;

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
        wait.until(ExpectedConditions.elementToBeClickable(allPostStatusFilter));

        var statusFilters = allPostStatusFilter.findElements(By.tagName("label"));
        try {
            statusFilters.getFirst().click();
        }
        catch (Exception _){}
        wait.until(ExpectedConditions.elementToBeClickable(clickNewPost));
        clickNewPost.click();
    }

    public int getPostCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(clickNewPost));
        wait.until(ExpectedConditions.elementToBeClickable(allPostStatusFilter));
        List<WebElement> statusFilters = allPostStatusFilter.findElements(By.tagName("label"));
        try {
            statusFilters.getFirst().click();
        } catch (Exception _) {

        }
        wait.until(ExpectedConditions.invisibilityOf(loadingSpinner));

        return posts.size();
    }
}
