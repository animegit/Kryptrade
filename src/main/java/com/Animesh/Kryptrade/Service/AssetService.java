package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Model.Asset;
import com.Animesh.Kryptrade.Model.Coin;
import com.Animesh.Kryptrade.Model.User;

import java.util.List;

public interface AssetService {

    Asset createAsset(User user, Coin coin,double quantity);

    Asset getAssetById(Long assetId) throws Exception;

    Asset getAssetsByUserId(Long userId,Long assetId);
    List<Asset> getUserAssets(Long userId);

    Asset updateAsset(Long assetId, double quantity) throws Exception;

    Asset findAssetByUserIdAndCoinId(Long userId, Long coinId) throws Exception;

    void deleteAsset(Long assetId) throws Exception;
}
