package com.hab.blog.feature.v1.home.service;

import com.hab.blog.feature.v1.entities.User.User;
import com.hab.blog.feature.v1.entities.astrology.AstrologyEvents;
import com.hab.blog.feature.v1.entities.astrology.Repository.UserAstrologyRepository;
import com.hab.blog.feature.v1.entities.astrology.Repository.UserDailyFortuneRepository;
import com.hab.blog.feature.v1.entities.astrology.UserAstrology;
import com.hab.blog.feature.v1.entities.astrology.UserDailyFortune;
import com.hab.blog.feature.v1.entities.User.Repository.UserRepository;
import com.hab.blog.feature.v1.home.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class HomeService {

    @Autowired
    private UserAstrologyRepository userAstrologyRepository;

    @Autowired
    private UserDailyFortuneRepository userDailyFortuneRepository;

    @Autowired
    private UserRepository userRepository;

    public HomePageResponse getHomePageData() {

        // 从 SecurityContext 中获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findUsersByUserName(username);
        // 1. 获取日期、星期
        assert user.orElse(null) != null;
        Optional<UserAstrology> userAstrology = getUserAstrologyForToday(user.orElseThrow().getId());

        if (userAstrology.isPresent()) {
            System.out.println("用户当天的星象解读: " + userAstrology.get().getPersonalInterpretation());
        } else {
            System.out.println("未找到用户当天的星象解读。");
        }

        // 3. 获取用户运势评分和提示
        Optional<UserDailyFortune> fortune = getUserDailyFortuneForToday(user.orElseThrow().getId());

        // 4. 构造响应数据
        HomePageResponse response = getHomePageResponse(userAstrology.orElseThrow(),fortune.orElseThrow(), user.orElseThrow().getProfileComplete());

        return response;
    }

    public HomePageResponse getHomePageResponse(UserAstrology userAstrology, UserDailyFortune userDailyFortune, boolean hasPersonalInfo) {

        // 构造 HomePageResponse
        return createHomePageResponse(toUserAstrologyResponse(userAstrology), toUserDailyFortuneResponse(userDailyFortune), hasPersonalInfo);
    }

    public HomePageResponse createHomePageResponse(UserAstrologyResponse userAstrologyResponse,
                                                  UserDailyFortuneResponse userDailyFortuneResponse,
                                                  boolean hasPersonalInfo) {

        // 构造核心功能模块
        List<HomePageCoreFeatureResponse> coreFeatures = Arrays.asList(
                new HomePageCoreFeatureResponse("塔罗", "点击进入塔罗占卜界面，用户可选择占卜方式（如三张牌阵、爱情占卜等）。", "/tarot"),
                new HomePageCoreFeatureResponse("星盘", "点击进入个人星盘分析界面，用户输入个人信息后获取星盘解读。", "/birth-chart"),
                new HomePageCoreFeatureResponse("合盘", "点击进入两人合盘界面，输入双方出生信息后获取关系解读。", "/synastry")
        );

        // 构造广告模块
        AdsResponse adsResponse;
        if (hasPersonalInfo) {
            adsResponse = new AdsResponse(Arrays.asList("/images/ad1.jpg", "/images/ad2.jpg", "/images/ad3.jpg"));
        } else {
            adsResponse = new AdsResponse("/images/default-ad.jpg");
        }



        // 返回完整的 HomePageResponse
        return new HomePageResponse(
                LocalDate.now(),
                LocalDate.now().getDayOfWeek().toString(),
                userAstrologyResponse,
                userDailyFortuneResponse,
                coreFeatures,
                adsResponse, createAboutUsResponse()
        );
    }


    public UserAstrologyResponse toUserAstrologyResponse(UserAstrology userAstrology) {
        AstrologyEvents event = userAstrology.getAstrologyEvent();

        return new UserAstrologyResponse(
                userAstrology.getId(),
                userAstrology.getPersonalInterpretation(),
                userAstrology.getCreatedAt(),
                event.getId(),
                event.getEventDate(),
                event.getPlanetPosition(),
                event.getZodiacChange(),
                event.getDescription()
        );
    }

    // 构造关于我们模块
    public AboutUsResponse createAboutUsResponse() {
        // 构造社交平台链接
        List<SocialLink> socialLinks = Arrays.asList(
                new SocialLink("/images/wechat-icon.png", "WeChat", "https://wechat.com/youraccount"),
                new SocialLink("/images/xiaohongshu-icon.png", "XiaoHongShu", "https://xiaohongshu.com/youraccount")
        );

        // 构造 AboutUsResponse
        return new AboutUsResponse(
                "我们是一个专业的团队，致力于通过星盘、塔罗等占卜工具为用户提供最精准的个人发展建议和预测。",
                "帮助用户更好地了解自己，探索未来，找到生活中的机会与挑战。",
                socialLinks
        );
    }

    public UserDailyFortuneResponse toUserDailyFortuneResponse(UserDailyFortune userDailyFortune) {
        return new UserDailyFortuneResponse(
                userDailyFortune.getId(),
                userDailyFortune.getOverallScore(),
                userDailyFortune.getLoveScore(),
                userDailyFortune.getWealthScore(),
                userDailyFortune.getCareerScore(),
                userDailyFortune.getEncouragementMessage(),
                userDailyFortune.getIsLocked(),
                userDailyFortune.getFortuneDate()
        );
    }


    public Optional<UserAstrology> getUserAstrologyForToday(Long userId) {
        LocalDate today = LocalDate.now();  // 获取当前日期
        return userAstrologyRepository.findByUserIdAndCurrentDate(userId, today);
    }

    // 获取用户当天的运势
    public Optional<UserDailyFortune> getUserDailyFortuneForToday(Long userId) {
        LocalDate today = LocalDate.now();  // 获取当前日期
        return userDailyFortuneRepository.findFirstByUserIdAndFortuneDate(userId, today);
    }
}

