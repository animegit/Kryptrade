package com.Animesh.Kryptrade.Service;

import com.Animesh.Kryptrade.Model.Asset;
import com.Animesh.Kryptrade.Model.Coin;
import com.Animesh.Kryptrade.Model.User;
import com.Animesh.Kryptrade.Repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        return assetRepository.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) throws Exception {
        Optional<Asset> asset = assetRepository.findById(assetId);
        if (asset.isEmpty()) {
            throw new Exception("Asset not found");
        }
        return asset.get();
    }

    @Override
    public Asset getAssetsByUserId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUserAssets(Long userId) {
        return assetRepository.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {
        Asset asset = getAssetById(assetId);
        asset.setQuantity(quantity+asset.getQuantity());
        return assetRepository.save(asset);
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, Long coinId) throws Exception {

        return  assetRepository.findByUserIdAndId(userId,coinId);

    }

    @Override
    public void deleteAsset(Long assetId) throws Exception {
        Asset asset = getAssetById(assetId);
        assetRepository.delete(asset);
    }
}
