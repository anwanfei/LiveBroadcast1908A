package com.example.finalproject.bean;

public class ComPoseBean {
    private BannerBean bannerBean;
    private PublicBean publicBean;

    public ComPoseBean(BannerBean bannerBean, PublicBean publicBean) {
        this.bannerBean = bannerBean;
        this.publicBean = publicBean;
    }

    public BannerBean getBannerBean() {
        return bannerBean;
    }

    public void setBannerBean(BannerBean bannerBean) {
        this.bannerBean = bannerBean;
    }

    public PublicBean getPublicBean() {
        return publicBean;
    }

    public void setPublicBean(PublicBean publicBean) {
        this.publicBean = publicBean;
    }
}
