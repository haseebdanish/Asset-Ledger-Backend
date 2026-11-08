package com.haseeb.assetledger.Service;

import com.haseeb.assetledger.Dto.AssetRequestDto;
import com.haseeb.assetledger.Dto.AssetResponseDto;
import com.haseeb.assetledger.Dto.PortfolioAllocationDto;
import com.haseeb.assetledger.Dto.PortfolioSummaryDto;
import com.haseeb.assetledger.Exception.AssetNotFoundException;
import com.haseeb.assetledger.Exception.UserNotFoundException;
import com.haseeb.assetledger.Model.Asset;
import com.haseeb.assetledger.Model.AssetType;
import com.haseeb.assetledger.Model.User;
import com.haseeb.assetledger.Repository.AssetRepository;
import com.haseeb.assetledger.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AssetService assetService;

    @Test
    void shouldAggregateExistingAsset() {

        // Arrange

        String email = "test@example.com";

        User user = mock(User.class);

        Asset existingAsset = new Asset();
        existingAsset.setId(1L);
        existingAsset.setUser(user);
        existingAsset.setAssetName("Bitcoin");
        existingAsset.setAssetType(AssetType.CRYPTO);
        existingAsset.setQuantity(new BigDecimal("0.5"));
        existingAsset.setInvestedAmount(new BigDecimal("30000"));

        AssetRequestDto request = new AssetRequestDto(
                "Bitcoin",
                AssetType.CRYPTO,
                new BigDecimal("0.2"),
                new BigDecimal("12000")
        );

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByUserAndAssetNameAndAssetType(
                user, "Bitcoin", AssetType.CRYPTO))
                .thenReturn(Optional.of(existingAsset));

        when(assetRepository.save(existingAsset))
                .thenReturn(existingAsset);


        // Act

        AssetResponseDto response =
                assetService.addOrUpdateAsset(email, request);


        // Assert

        assertEquals("Bitcoin", response.assetName());
        assertEquals(AssetType.CRYPTO, response.assetType());
        assertEquals(new BigDecimal("0.7"), response.quantity());
        assertEquals(new BigDecimal("42000"), response.investedAmount());

        verify(userRepository, times(1)).findByEmail(email);

        verify(assetRepository, times(1))
                .findByUserAndAssetNameAndAssetType(
                        user, "Bitcoin", AssetType.CRYPTO);

        verify(assetRepository, times(1)).save(existingAsset);
    }


    @Test
    void shouldCreateNewAssetWhenNoExistingAssetIsFound() {

        // Arrange

        String email = "test@example.com";

        User user = mock(User.class);

        AssetRequestDto request = new AssetRequestDto(
                "Ethereum",
                AssetType.CRYPTO,
                new BigDecimal("2"),
                new BigDecimal("50000")
        );

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByUserAndAssetNameAndAssetType(
                user, "Ethereum", AssetType.CRYPTO))
                .thenReturn(Optional.empty());

        when(assetRepository.save(any(Asset.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        // Act

        AssetResponseDto response =
                assetService.addOrUpdateAsset(email, request);


        // Assert

        assertEquals("Ethereum", response.assetName());
        assertEquals(AssetType.CRYPTO, response.assetType());
        assertEquals(new BigDecimal("2"), response.quantity());
        assertEquals(new BigDecimal("50000"), response.investedAmount());

        verify(assetRepository, times(1)).save(any(Asset.class));
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        // Arrange

        String email = "unknown@example.com";

        AssetRequestDto request = new AssetRequestDto(
                "Bitcoin",
                AssetType.CRYPTO,
                new BigDecimal("0.5"),
                new BigDecimal("30000")
        );

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());


        // Act & Assert

        assertThrows(UserNotFoundException.class,
                () -> assetService.addOrUpdateAsset(email, request));

        verify(userRepository, times(1)).findByEmail(email);

        verifyNoInteractions(assetRepository);
    }

    @Test
    void shouldThrowExceptionWhenAssetDoesNotBelongToUser() {

        // Arrange

        String email = "test@example.com";
        Long assetId = 1L;

        User user = mock(User.class);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByIdAndUser(assetId, user))
                .thenReturn(Optional.empty());


        // Act & Assert

        assertThrows(AssetNotFoundException.class,
                () -> assetService.getAssetById(email, assetId));

        verify(assetRepository, times(1))
                .findByIdAndUser(assetId, user);
    }

    @Test
    void shouldNotDeleteAssetWhenItDoesNotBelongToUser() {

        // Arrange

        String email = "test@example.com";
        Long assetId = 1L;

        User user = mock(User.class);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByIdAndUser(assetId, user))
                .thenReturn(Optional.empty());


        // Act & Assert

        assertThrows(AssetNotFoundException.class,
                () -> assetService.deleteAsset(email, assetId));

        verify(assetRepository, times(1))
                .findByIdAndUser(assetId, user);

        verify(assetRepository, never())
                .delete(any(Asset.class));
    }

    @Test
    void shouldNotUpdateAssetWhenItDoesNotBelongToUser() {

        // Arrange

        String email = "test@example.com";
        Long assetId = 1L;

        User user = mock(User.class);

        AssetRequestDto request = new AssetRequestDto(
                "Bitcoin",
                AssetType.CRYPTO,
                new BigDecimal("1"),
                new BigDecimal("60000")
        );

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByIdAndUser(assetId, user))
                .thenReturn(Optional.empty());


        // Act & Assert

        assertThrows(AssetNotFoundException.class,
                () -> assetService.updateAsset(email, assetId, request));

        verify(assetRepository, times(1))
                .findByIdAndUser(assetId, user);

        verify(assetRepository, never())
                .save(any(Asset.class));
    }

    @Test
    void shouldUpdateAssetSuccessfully() {

        // Arrange

        String email = "test@example.com";
        Long assetId = 1L;

        User user = mock(User.class);

        Asset existingAsset = new Asset();
        existingAsset.setId(assetId);
        existingAsset.setUser(user);
        existingAsset.setAssetName("Bitcoin");
        existingAsset.setAssetType(AssetType.CRYPTO);
        existingAsset.setQuantity(new BigDecimal("0.5"));
        existingAsset.setInvestedAmount(new BigDecimal("30000"));

        AssetRequestDto request = new AssetRequestDto(
                "Ethereum",
                AssetType.CRYPTO,
                new BigDecimal("2"),
                new BigDecimal("50000")
        );

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByIdAndUser(assetId, user))
                .thenReturn(Optional.of(existingAsset));

        when(assetRepository.save(existingAsset))
                .thenReturn(existingAsset);


        // Act

        AssetResponseDto response =
                assetService.updateAsset(email, assetId, request);


        // Assert

        assertEquals("Ethereum", response.assetName());
        assertEquals(AssetType.CRYPTO, response.assetType());
        assertEquals(new BigDecimal("2"), response.quantity());
        assertEquals(new BigDecimal("50000"), response.investedAmount());

        verify(assetRepository, times(1)).save(existingAsset);
    }

    @Test
    void shouldReturnUserNetworth() {

        // Arrange

        String email = "test@example.com";

        User user = mock(User.class);

        BigDecimal expectedNetworth = new BigDecimal("150000");

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.getTotalInvestedByUser(user))
                .thenReturn(expectedNetworth);


        // Act

        BigDecimal actualNetworth = assetService.getNetworth(email);


        // Assert

        assertEquals(expectedNetworth, actualNetworth);

        verify(userRepository, times(1)).findByEmail(email);

        verify(assetRepository, times(1))
                .getTotalInvestedByUser(user);
    }

    @Test
    void shouldReturnZeroNetworthWhenUserHasNoAssets() {

        // Arrange

        String email = "test@example.com";

        User user = mock(User.class);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.getTotalInvestedByUser(user))
                .thenReturn(null);


        // Act

        BigDecimal actualNetworth = assetService.getNetworth(email);


        // Assert

        assertEquals(BigDecimal.ZERO, actualNetworth);

        verify(assetRepository, times(1))
                .getTotalInvestedByUser(user);
    }

    @Test
    void shouldReturnAllAssetsForUser() {

        // Arrange

        String email = "test@example.com";

        User user = mock(User.class);

        Asset bitcoin = new Asset();
        bitcoin.setId(1L);
        bitcoin.setUser(user);
        bitcoin.setAssetName("Bitcoin");
        bitcoin.setAssetType(AssetType.CRYPTO);
        bitcoin.setQuantity(new BigDecimal("0.5"));
        bitcoin.setInvestedAmount(new BigDecimal("30000"));

        Asset ethereum = new Asset();
        ethereum.setId(2L);
        ethereum.setUser(user);
        ethereum.setAssetName("Ethereum");
        ethereum.setAssetType(AssetType.CRYPTO);
        ethereum.setQuantity(new BigDecimal("2"));
        ethereum.setInvestedAmount(new BigDecimal("50000"));

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByUser(user))
                .thenReturn(List.of(bitcoin, ethereum));


        // Act

        List<AssetResponseDto> response =
                assetService.getUserAssets(email);


        // Assert

        assertEquals(2, response.size());

        assertEquals("Bitcoin", response.get(0).assetName());
        assertEquals(new BigDecimal("30000"), response.get(0).investedAmount());

        assertEquals("Ethereum", response.get(1).assetName());
        assertEquals(new BigDecimal("50000"), response.get(1).investedAmount());

        verify(assetRepository, times(1)).findByUser(user);
    }

    @Test
    void shouldReturnCorrectPortfolioSummary() {

        // Arrange

        String email = "test@example.com";

        User user = mock(User.class);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.getTotalInvestedByUser(user))
                .thenReturn(new BigDecimal("150000"));

        when(assetRepository.countByUser(user))
                .thenReturn(6L);

        when(assetRepository.getTotalInvestmentByType(user, AssetType.STOCK))
                .thenReturn(new BigDecimal("40000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.MUTUAL_FUND))
                .thenReturn(new BigDecimal("30000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.CRYPTO))
                .thenReturn(new BigDecimal("20000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.GOLD))
                .thenReturn(new BigDecimal("15000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.BANK))
                .thenReturn(new BigDecimal("25000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.REAL_ESTATE))
                .thenReturn(new BigDecimal("20000"));


        // Act

        PortfolioSummaryDto response =
                assetService.getPortfolioSummary(email);


        // Assert

        assertEquals(new BigDecimal("150000"), response.totalInvestment());
        assertEquals(6L, response.totalAssets());

        assertEquals(new BigDecimal("40000"), response.stockInvestment());
        assertEquals(new BigDecimal("30000"), response.mutualFundInvestment());
        assertEquals(new BigDecimal("20000"), response.cryptoInvestment());
        assertEquals(new BigDecimal("15000"), response.goldInvestment());
        assertEquals(new BigDecimal("25000"), response.bankInvestment());
        assertEquals(new BigDecimal("20000"), response.realEstateInvestment());
    }

    @Test
    void shouldReturnZeroForAssetCategoriesWithNullValues() {

        // Arrange

        String email = "test@example.com";

        User user = mock(User.class);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.getTotalInvestedByUser(user))
                .thenReturn(new BigDecimal("50000"));

        when(assetRepository.countByUser(user))
                .thenReturn(2L);

        when(assetRepository.getTotalInvestmentByType(user, AssetType.STOCK))
                .thenReturn(new BigDecimal("30000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.MUTUAL_FUND))
                .thenReturn(null);

        when(assetRepository.getTotalInvestmentByType(user, AssetType.CRYPTO))
                .thenReturn(null);

        when(assetRepository.getTotalInvestmentByType(user, AssetType.GOLD))
                .thenReturn(new BigDecimal("20000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.BANK))
                .thenReturn(null);

        when(assetRepository.getTotalInvestmentByType(user, AssetType.REAL_ESTATE))
                .thenReturn(null);


        // Act

        PortfolioSummaryDto response =
                assetService.getPortfolioSummary(email);


        // Assert

        assertEquals(new BigDecimal("50000"), response.totalInvestment());
        assertEquals(2L, response.totalAssets());

        assertEquals(new BigDecimal("30000"), response.stockInvestment());
        assertEquals(BigDecimal.ZERO, response.mutualFundInvestment());
        assertEquals(BigDecimal.ZERO, response.cryptoInvestment());
        assertEquals(new BigDecimal("20000"), response.goldInvestment());
        assertEquals(BigDecimal.ZERO, response.bankInvestment());
        assertEquals(BigDecimal.ZERO, response.realEstateInvestment());
    }

    @Test
    void shouldCalculatePortfolioAllocationCorrectly() {

        // Arrange

        String email = "test@example.com";
        User user = mock(User.class);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.getTotalInvestedByUser(user))
                .thenReturn(new BigDecimal("100000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.STOCK))
                .thenReturn(new BigDecimal("40000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.MUTUAL_FUND))
                .thenReturn(new BigDecimal("30000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.CRYPTO))
                .thenReturn(new BigDecimal("20000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.GOLD))
                .thenReturn(new BigDecimal("5000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.BANK))
                .thenReturn(new BigDecimal("3000"));

        when(assetRepository.getTotalInvestmentByType(user, AssetType.REAL_ESTATE))
                .thenReturn(new BigDecimal("2000"));


        // Act

        List<PortfolioAllocationDto> response =
                assetService.getAllocation(email);


        // Assert

        assertEquals(6, response.size());

        PortfolioAllocationDto stock = response.stream()
                .filter(a -> a.assetType().equals("STOCK"))
                .findFirst()
                .orElseThrow();

        PortfolioAllocationDto mutualFund = response.stream()
                .filter(a -> a.assetType().equals("MUTUAL_FUND"))
                .findFirst()
                .orElseThrow();

        PortfolioAllocationDto crypto = response.stream()
                .filter(a -> a.assetType().equals("CRYPTO"))
                .findFirst()
                .orElseThrow();

        PortfolioAllocationDto gold = response.stream()
                .filter(a -> a.assetType().equals("GOLD"))
                .findFirst()
                .orElseThrow();

        PortfolioAllocationDto bank = response.stream()
                .filter(a -> a.assetType().equals("BANK"))
                .findFirst()
                .orElseThrow();

        PortfolioAllocationDto realEstate = response.stream()
                .filter(a -> a.assetType().equals("REAL_ESTATE"))
                .findFirst()
                .orElseThrow();

        assertEquals(new BigDecimal("40000"), stock.investment());
        assertEquals(40.0, stock.percentage(), 0.001);

        assertEquals(new BigDecimal("30000"), mutualFund.investment());
        assertEquals(30.0, mutualFund.percentage(), 0.001);

        assertEquals(new BigDecimal("20000"), crypto.investment());
        assertEquals(20.0, crypto.percentage(), 0.001);

        assertEquals(new BigDecimal("5000"), gold.investment());
        assertEquals(5.0, gold.percentage(), 0.001);

        assertEquals(new BigDecimal("3000"), bank.investment());
        assertEquals(3.0, bank.percentage(), 0.001);

        assertEquals(new BigDecimal("2000"), realEstate.investment());
        assertEquals(2.0, realEstate.percentage(), 0.001);
    }


    @Test
    void shouldReturnZeroAllocationWhenPortfolioIsEmpty() {

        // Arrange

        String email = "test@example.com";
        User user = mock(User.class);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.getTotalInvestedByUser(user))
                .thenReturn(BigDecimal.ZERO);

        for (AssetType type : AssetType.values()) {
            when(assetRepository.getTotalInvestmentByType(user, type))
                    .thenReturn(BigDecimal.ZERO);
        }


        // Act

        List<PortfolioAllocationDto> response =
                assetService.getAllocation(email);


        // Assert

        assertEquals(AssetType.values().length, response.size());

        for (PortfolioAllocationDto allocation : response) {
            assertEquals(BigDecimal.ZERO, allocation.investment());
            assertEquals(0.0, allocation.percentage(), 0.001);
        }
    }

    @Test
    void shouldReturnPaginatedUserAssets() {

        // Arrange

        String email = "test@example.com";
        int page = 0;
        int size = 2;

        User user = mock(User.class);

        Asset bitcoin = new Asset();
        bitcoin.setId(1L);
        bitcoin.setAssetName("Bitcoin");
        bitcoin.setAssetType(AssetType.CRYPTO);
        bitcoin.setQuantity(new BigDecimal("0.5"));
        bitcoin.setInvestedAmount(new BigDecimal("30000"));

        Asset ethereum = new Asset();
        ethereum.setId(2L);
        ethereum.setAssetName("Ethereum");
        ethereum.setAssetType(AssetType.CRYPTO);
        ethereum.setQuantity(new BigDecimal("2"));
        ethereum.setInvestedAmount(new BigDecimal("50000"));

        Pageable pageable = PageRequest.of(page, size);

        Page<Asset> assetPage = new PageImpl<>(
                List.of(bitcoin, ethereum),
                pageable,
                5
        );

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByUser(eq(user), any(Pageable.class)))
                .thenReturn(assetPage);


        // Act

        Page<AssetResponseDto> response =
                assetService.getUserAssets(email, page, size);


        // Assert

        assertEquals(2, response.getContent().size());
        assertEquals(5, response.getTotalElements());
        assertEquals(3, response.getTotalPages());
        assertEquals(0, response.getNumber());
        assertEquals(2, response.getSize());

        assertEquals("Bitcoin", response.getContent().get(0).assetName());
        assertEquals("Ethereum", response.getContent().get(1).assetName());

        verify(assetRepository).findByUser(eq(user), eq(pageable));
    }

    @Test
    void shouldReturnEmptyPageWhenNoAssetsExistOnRequestedPage() {

        // Arrange

        String email = "test@example.com";
        int page = 5;
        int size = 2;

        User user = mock(User.class);

        Pageable pageable = PageRequest.of(page, size);

        Page<Asset> emptyPage = new PageImpl<>(
                List.of(),
                pageable,
                5
        );

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByUser(eq(user), any(Pageable.class)))
                .thenReturn(emptyPage);


        // Act

        Page<AssetResponseDto> response =
                assetService.getUserAssets(email, page, size);


        // Assert

        assertTrue(response.isEmpty());
        assertEquals(5, response.getTotalElements());
        assertEquals(0, response.getContent().size());
    }

    @Test
    void shouldReturnAssetsFilteredByType() {

        // Arrange

        String email = "test@example.com";
        User user = mock(User.class);

        Asset bitcoin = new Asset();
        bitcoin.setId(1L);
        bitcoin.setAssetName("Bitcoin");
        bitcoin.setAssetType(AssetType.CRYPTO);
        bitcoin.setQuantity(new BigDecimal("0.5"));
        bitcoin.setInvestedAmount(new BigDecimal("30000"));

        Asset ethereum = new Asset();
        ethereum.setId(2L);
        ethereum.setAssetName("Ethereum");
        ethereum.setAssetType(AssetType.CRYPTO);
        ethereum.setQuantity(new BigDecimal("2"));
        ethereum.setInvestedAmount(new BigDecimal("50000"));

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByUserAndAssetType(user, AssetType.CRYPTO))
                .thenReturn(List.of(bitcoin, ethereum));


        // Act

        List<AssetResponseDto> response =
                assetService.getAssetsByType(email, AssetType.CRYPTO);


        // Assert

        assertEquals(2, response.size());

        assertEquals("Bitcoin", response.get(0).assetName());
        assertEquals(AssetType.CRYPTO, response.get(0).assetType());

        assertEquals("Ethereum", response.get(1).assetName());
        assertEquals(AssetType.CRYPTO, response.get(1).assetType());

        verify(assetRepository, times(1))
                .findByUserAndAssetType(user, AssetType.CRYPTO);
    }

    @Test
    void shouldReturnAssetsMatchingSearchKeyword() {

        // Arrange

        String email = "test@example.com";
        String keyword = "bit";

        User user = mock(User.class);

        Asset bitcoin = new Asset();
        bitcoin.setId(1L);
        bitcoin.setAssetName("Bitcoin");
        bitcoin.setAssetType(AssetType.CRYPTO);
        bitcoin.setQuantity(new BigDecimal("0.5"));
        bitcoin.setInvestedAmount(new BigDecimal("30000"));

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(assetRepository.findByUserAndAssetNameContainingIgnoreCase(
                user, keyword))
                .thenReturn(List.of(bitcoin));


        // Act

        List<AssetResponseDto> response =
                assetService.searchAssets(email, keyword);


        // Assert

        assertEquals(1, response.size());

        assertEquals("Bitcoin", response.get(0).assetName());
        assertEquals(AssetType.CRYPTO, response.get(0).assetType());
        assertEquals(new BigDecimal("30000"), response.get(0).investedAmount());

        verify(assetRepository, times(1))
                .findByUserAndAssetNameContainingIgnoreCase(user, keyword);
    }

}
