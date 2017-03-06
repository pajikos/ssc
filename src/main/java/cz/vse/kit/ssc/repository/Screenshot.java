/**
 *
 */
package cz.vse.kit.ssc.repository;

import java.util.Date;

import org.openqa.selenium.Platform;

/**
 * @author pavel.sklenar
 *
 */
public final class Screenshot {

    private String id;
    private Platform platform;
    private String browserName;
    private String browserVersion = "";
    private byte[] imageData;
    private Date captureDate;

    /**
     * Dummy constructor
     */
    public Screenshot() {
        // Do nothing
    }

    /**
     * Clone constructor
     *
     * @param screenshot
     */
    public Screenshot(Screenshot screenshot) {
        this.id = screenshot.getId();
        this.platform = screenshot.getPlatform();
        this.browserName = screenshot.getBrowserName();
        this.browserVersion = screenshot.getBrowserVersion();
        this.imageData = screenshot.getImageData();
        this.captureDate = screenshot.getCaptureDate();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the platform
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * @param os
     *            the platform to set
     */
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    /**
     * @return the browserName
     */
    public String getBrowserName() {
        return browserName;
    }

    /**
     * @param browserName
     *            the browserName to set
     */
    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    /**
     * @return the browserVersion
     */
    public String getBrowserVersion() {
        return browserVersion;
    }

    /**
     * @param browserVersion
     *            the browserVersion to set
     */
    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    /**
     * @return the imageData
     */
    public byte[] getImageData() {
        return imageData;
    }

    /**
     * @param imageData
     *            the imageData to set
     */
    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Date getCaptureDate() {
        return captureDate;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Screenshot [id=" + id + ", platform=" + platform + ", browserName=" + browserName + ", browserVersion="
                + browserVersion + ", captureTime=" + captureDate + "]";
    }

    /**
     * @param captureDate
     *            the captureDate to set
     */
    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((browserName == null) ? 0 : browserName.hashCode());
        result = prime * result + ((browserVersion == null) ? 0 : browserVersion.hashCode());
        result = prime * result + ((captureDate == null) ? 0 : captureDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((platform == null) ? 0 : platform.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Screenshot other = (Screenshot) obj;
        if (browserName == null) {
            if (other.browserName != null)
                return false;
        } else if (!browserName.equals(other.browserName))
            return false;
        if (browserVersion == null) {
            if (other.browserVersion != null)
                return false;
        } else if (!browserVersion.equals(other.browserVersion))
            return false;
        if (captureDate == null) {
            if (other.captureDate != null)
                return false;
        } else if (!captureDate.equals(other.captureDate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (platform == null) {
            if (other.platform != null)
                return false;
        } else if (!platform.is(other.platform))
            return false;
        return true;
    }

}
