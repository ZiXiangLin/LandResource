package com.gis.demo.domain.ModelParament;

public class StreamExtraction {
    private String DEMurl;
    private String threshold;
    private String fillurl ;
    private String flowdirectionurl ;
    private String flowaccumulationurl ;
    private String flowthresholdurl  ;
    private String rester_to_vectorurl  ;

    public String getDEMurl() {
        return DEMurl;
    }

    public String getThreshold() {
        return threshold;
    }

    public String getFillurl() {
        return fillurl;
    }

    public String getFlowdirectionurl() {
        return flowdirectionurl;
    }

    public String getFlowaccumulationurl() {
        return flowaccumulationurl;
    }

    public String getFlowthresholdurl() {
        return flowthresholdurl;
    }

    public String getRester_to_vectorurl() {
        return rester_to_vectorurl;
    }

    public void setDEMurl(String DEMurl) {
        this.DEMurl = DEMurl;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public void setFillurl(String fillurl) {
        this.fillurl = fillurl;
    }

    public void setFlowdirectionurl(String flowdirectionurl) {
        this.flowdirectionurl = flowdirectionurl;
    }

    public void setFlowaccumulationurl(String flowaccumulationurl) {
        this.flowaccumulationurl = flowaccumulationurl;
    }

    public void setFlowthresholdurl(String flowthresholdurl) {
        this.flowthresholdurl = flowthresholdurl;
    }

    public void setRester_to_vectorurl(String rester_to_vectorurl) {
        this.rester_to_vectorurl = rester_to_vectorurl;
    }
}
