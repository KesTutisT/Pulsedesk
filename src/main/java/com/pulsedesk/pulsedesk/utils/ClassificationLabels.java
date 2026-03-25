package com.pulsedesk.pulsedesk.utils;

import java.util.Map;

public class ClassificationLabels {

    private ClassificationLabels() {}

    public static final String FEEDBACK = "real user feedback about a software product or service";
    public static final String NONSENSE = "nonsense, gibberish, or unrelated to software";

    public static final String PROBLEM = "problem or complaint";
    public static final String COMPLIMENT = "compliment or praise";

    public static final String CRITICAL = "critical system failure or data loss";
    public static final String MINOR = "minor or moderate issue";
    public static final String BROKEN = "something is not working or broken";
    public static final String COSMETIC = "visual or cosmetic issue like font color or spelling";

    public static final String CAT_BUG = "software or button not working as expected";
    public static final String CAT_BILLING = "payment or invoice issue";
    public static final String CAT_FEATURE = "new feature or improvement request";
    public static final String CAT_ACCOUNT = "problem to my specific account, such as being locked, banned, or losing profile data";
    public static final String CAT_OTHER = "other";

    public static final Map<String, String> CATEGORY_MAP = Map.of(
            CAT_BUG,      "bug",
            CAT_BILLING,  "billing",
            CAT_FEATURE,  "feature",
            CAT_ACCOUNT,  "account",
            CAT_OTHER,    "other"
    );
}
