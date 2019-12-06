package lathigara.harsh.flipkart.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import lathigara.harsh.flipkart.Fragments.ProductDescriptionFragment;
import lathigara.harsh.flipkart.Fragments.ProductSpecificationFragment;
import lathigara.harsh.flipkart.Models.ProductSpecificationModel;

public class ProductDetailsAdapter extends FragmentPagerAdapter {
    private int totalTabs;
    private String productDescription,productOtherDetails;
    private List<ProductSpecificationModel>productSpecificationModelList;

    public ProductDetailsAdapter(FragmentManager fm,int totalTabs, String productDescription, String productOtherDetails, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.totalTabs = totalTabs;
        this.productDescription = productDescription;
        this.productOtherDetails = productOtherDetails;
        this.productSpecificationModelList = productSpecificationModelList;
    }



    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment();
                productDescriptionFragment.body = productDescription;

                return productDescriptionFragment;
            case 1:

                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                ProductSpecificationFragment.productSpecificationModelList = productSpecificationModelList;
                return productSpecificationFragment;
            case 2:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                productDescriptionFragment1.body = productOtherDetails;
                return productDescriptionFragment1;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
