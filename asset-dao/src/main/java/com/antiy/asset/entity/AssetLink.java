package com.antiy.asset.entity;

public class AssetLink {
    private String  assetId;
    private Integer categoryModal;
    private String  parentAssetId;
    private Integer parentCategoryModal;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getCategoryModal() {
        return categoryModal;
    }

    public void setCategoryModal(Integer categoryModal) {
        this.categoryModal = categoryModal;
    }

    public String getParentAssetId() {
        return parentAssetId;
    }

    public void setParentAssetId(String parentAssetId) {
        this.parentAssetId = parentAssetId;
    }

    public Integer getParentCategoryModal() {
        return parentCategoryModal;
    }

    public void setParentCategoryModal(Integer parentCategoryModal) {
        this.parentCategoryModal = parentCategoryModal;
    }
}
