package com.example.msdisplay;

import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

/* loaded from: classes.dex */
public class Util {
    public static final int MAX_NUM_POINTS = 3000;
    private static final byte FRAMERATE_MARGIN = 2;
    public static Select_tim[] timing = {new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_64_640X480_60, "640X480_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_65_640X480_75, "640X480_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_66_800X600_60, "800X600_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_68_800X600_75, "800X600_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_71_1024X768_60, "1024X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_73_1024X768_75, "1024X768_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_04_1280x720P_60HZ, "1280X720_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_84_1280X768_60, "1280X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_86_1280X768_75, "1280X768_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_96_1280X1024_60, "1280X1024_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_97_1280X1024_75, "1280X1024_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_100_1360X768_60, "1360X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_102_1366X768_60, "1366X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_103_1400X1050_60, "1400X1050_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_107_1440X900_60_DMT, "1440X900_60_DMT"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_115_1600X1200_60, "1600X1200_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_120_1680X1050_60, "1680X1050_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_129_1920X1080_60_DMT, "1920X1080_60_DMT"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_133_1920X1200_60_DMT_RB, "1920X1200_60_DMT_RB"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_02_720x480P_60HZ, "480P"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_17_720x576P_50HZ, "576P")};
    public static AS7160_MISC_TIMING[] g_arrTimingTable = {new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_CEA_02_720x480P_60HZ, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVNegHNeg, 858, 525, 720, 480, 2700, 6000, 122, 36, 62, 6)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_CEA_04_1280x720P_60HZ, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 1650, 750, 1280, 720, 7425, 6000, 260, 25, 40, 5)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_CEA_17_720x576P_50HZ, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVNegHNeg, 864, 625, 720, 576, 2700, 5000, 132, 44, 64, 5)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_CEA_19_1280x720P_50HZ, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 1980, 750, 1280, 720, 7425, 5000, 260, 25, 40, 5)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_CEA_31_1920x1080P_50HZ, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 2640, 1125, 1920, 1080, 14850, 5000, 192, 41, 44, 5)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_CEA_34_1920x1080P_30HZ, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 2200, 1125, 1920, 1080, 7417, MAX_NUM_POINTS, 192, 41, 44, 5)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_64_640X480_60, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVNegHNeg, 800, 525, 640, 480, 2517, 5994, 144, 35, 96, 2)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_65_640X480_75, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVNegHNeg, 840, 500, 640, 480, 3150, 7500, 184, 19, 64, 3)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_66_800X600_60, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 1056, 628, 800, 600, 4000, 6032, 216, 27, 128, 4)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_68_800X600_75, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 1056, 625, 800, 600, 4950, 7500, 240, 24, 80, 3)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_71_1024X768_60, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVNegHNeg, 1344, 806, 1024, 768, 6500, 6000, 296, 35, 136, 6)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_73_1024X768_75, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 1312, 800, 1024, 768, 7875, 7503, 272, 31, 96, 3)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_79_1280X720_60_DMT, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 1650, 750, 1280, 720, 7425, 6000, 260, 25, 40, 5)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_84_1280X768_60, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHNeg, 1664, 798, 1280, 768, 7950, 5987, 320, 27, 128, 7)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_86_1280X768_75, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHNeg, 1696, 805, 1280, 768, 10225, 7489, 336, 34, 128, 7)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_96_1280X1024_60, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 1688, 1066, 1280, 1024, 10800, 6002, 360, 41, 112, 3)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_97_1280X1024_75, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 1688, 1066, 1280, 1024, 13500, 7502, 392, 41, 144, 3)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_100_1360X768_60, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 1792, 795, 1360, 768, 8550, 6002, 368, 24, 112, 6)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_102_1366X768_60, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 1792, 798, 1366, 768, 8550, 5979, 356, 27, 143, 3)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_103_1400X1050_60, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHNeg, 1864, 1089, 1400, 1050, 12175, 5998, 376, 36, 144, 4)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_107_1440X900_60_DMT, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHNeg, 1904, 934, 1440, 900, 10650, 5989, 384, 31, 152, 6)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_115_1600X1200_60, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 2160, 1250, 1600, 1200, 16200, 6000, 496, 49, 192, 3)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_120_1680X1050_60, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHNeg, 2240, 1089, 1680, 1050, 14625, 5995, 456, 36, 176, 6)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_129_1920X1080_60_DMT, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHPos, 2200, 1125, 1920, 1080, 14850, 6000, 190, 45, 88, 5)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_132_1920X1200_60_CVT, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVPosHNeg, 2592, 1242, 1920, 1200, 19312, 5999, 544, 41, 208, 3)), new AS7160_MISC_TIMING((byte) _E_AS7160_VIDEO_FORMAT_.VFMT_VESA_133_1920X1200_60_DMT_RB, new AS7160_VIDEO_TIMING((byte) _E_SYNC_POLARITY_.ProgrVNegHPos, 2080, 1235, 1920, 1200, 15400, 5995, 112, 32, 32, 6))};
    public static AS7160_SIMPLE_TIMING[] g_arrSimpleTimingTable = {new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_02_720x480P_60HZ, 720, 480, 2700, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_17_720x576P_50HZ, 720, 576, 2700, 50), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_19_1280x720P_50HZ, 1280, 720, 7425, 50), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_31_1920x1080P_50HZ, 1920, 1080, 14850, 50), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_34_1920x1080P_30HZ, 1920, 1080, 7417, 30), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_64_640X480_60, 640, 480, 2517, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_66_800X600_60, 800, 600, 4000, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_68_800X600_75, 800, 600, 4950, 75), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_71_1024X768_60, 1024, 768, 6500, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_73_1024X768_75, 1024, 768, 7875, 75), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_76_1152X864_60, 1152, 864, 8162, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_78_1280X600_60, 1280, 600, 6150, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_79_1280X720_60_DMT, 1280, 720, 7425, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_84_1280X768_60, 1280, 768, 7950, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_86_1280X768_75, 1280, 768, 10225, 75), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_87_1280X800_60, 1280, 800, 8350, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_91_1280X960_60_DMT, 1280, 960, 10800, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_96_1280X1024_60, 1280, 1024, 10800, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_97_1280X1024_75, 1280, 1024, 13500, 75), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_100_1360X768_60, 1360, 768, 8550, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_102_1366X768_60, 1366, 768, 8550, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_103_1400X1050_60, 1400, 1050, 12175, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_107_1440X900_60_DMT, 1440, 900, 10650, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_115_1600X1200_60, 1600, 1200, 16200, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_120_1680X1050_60, 1680, 1050, 14625, 60), new AS7160_SIMPLE_TIMING(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_129_1920X1080_60_DMT, 1920, 1080, 14850, 60)};
    public static Select_tim[] CVBSTiming = {new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_02_720x480P_60HZ, "NTSC"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_17_720x576P_50HZ, "PAL")};
    public static Select_tim[] SVIDEOTiming = {new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_02_720x480P_60HZ, "NTSC"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_17_720x576P_50HZ, "PAL")};
    public static Select_tim[] CSTiming = {new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_02_720x480P_60HZ, "NTSC"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_17_720x576P_50HZ, "PAL")};
    public static Select_tim[] YPbPrTiming = {new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_02_720x480P_60HZ, "480P"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_CEA_17_720x576P_50HZ, "576P"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_79_1280X720_60_DMT, "720P"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_129_1920X1080_60_DMT, "1080P")};
    public static Select_tim[] DigitalOutTiming = {new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_64_640X480_60, "640X480_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_66_800X600_60, "800X600_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_68_800X600_75, "800X600_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_71_1024X768_60, "1024X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_73_1024X768_75, "1024X768_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_79_1280X720_60_DMT, "1280X720_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_84_1280X768_60, "1280X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_86_1280X768_75, "1280X768_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_96_1280X1024_60, "1280X1024_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_97_1280X1024_75, "1280X1024_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_100_1360X768_60, "1360X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_102_1366X768_60, "1366X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_103_1400X1050_60, "1400X1050_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_107_1440X900_60_DMT, "1440X900_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_115_1600X1200_60, "1600X1200_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_120_1680X1050_60, "1680X1050_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_129_1920X1080_60_DMT, "1920X1080_60")};
    public static Select_tim[] VGADefaultTiming = {new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_64_640X480_60, "640X480_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_66_800X600_60, "800X600_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_68_800X600_75, "800X600_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_71_1024X768_60, "1024X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_73_1024X768_75, "1024X768_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_79_1280X720_60_DMT, "1280X720_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_84_1280X768_60, "1280X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_86_1280X768_75, "1280X768_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_96_1280X1024_60, "1280X1024_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_97_1280X1024_75, "1280X1024_75"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_100_1360X768_60, "1360X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_102_1366X768_60, "1366X768_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_103_1400X1050_60, "1400X1050_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_107_1440X900_60_DMT, "1440X900_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_115_1600X1200_60, "1600X1200_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_120_1680X1050_60, "1680X1050_60"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_129_1920X1080_60_DMT, "1920X1080_60")};
    public static Select_tim[] HDMIDefaultTiming = {new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_129_1920X1080_60_DMT, "1080P"), new Select_tim(_E_AS7160_VIDEO_FORMAT_.VFMT_VESA_79_1280X720_60_DMT, "720P")};
    public static boolean isUsingEDID = false;

    /* loaded from: classes.dex */
    public static class SDRAM_TYPE {
        public static int SDRAM_16M = 3;
        public static int SDRAM_2M = 0;
        public static int SDRAM_4M = 1;
        public static int SDRAM_8M = 2;
        public static int SDRAM_NONE = 4;
    }

    /* loaded from: classes.dex */
    public static class VideoDisplay {
        public byte colorSpace;
        public byte frameRate;
        public int height;
        public byte port;
        public int width;
    }

    /* loaded from: classes.dex */
    public static class VideoIn {
        public byte colorSpace;
        public byte frameRate;
        public int height;
        public int refreshHeight;
        public int refreshWidth;
        public int refreshX;
        public int refreshY;
        public int width;
    }

    /* loaded from: classes.dex */
    public static class _E_AS7160_VIDEO_FORMAT_ {
        public static int VFMT_CEA_01_640x480P_60HZ = 1;
        public static int VFMT_CEA_02_720x480P_60HZ = 2;
        public static int VFMT_CEA_03_720x480P_60HZ = 3;
        public static int VFMT_CEA_04_1280x720P_60HZ = 4;
        public static int VFMT_CEA_05_1920x1080I_60HZ = 5;
        public static int VFMT_CEA_06_720x480I_60HZ = 6;
        public static int VFMT_CEA_07_720x480I_60HZ = 7;
        public static int VFMT_CEA_08_720x240P_60HZ = 8;
        public static int VFMT_CEA_09_720x240P_60HZ = 9;
        public static int VFMT_CEA_10_720x480I_60HZ = 10;
        public static int VFMT_CEA_11_720x480I_60HZ = 11;
        public static int VFMT_CEA_12_720x240P_60HZ = 12;
        public static int VFMT_CEA_13_720x240P_60HZ = 13;
        public static int VFMT_CEA_14_1440x480P_60HZ = 14;
        public static int VFMT_CEA_15_1440x480P_60HZ = 15;
        public static int VFMT_CEA_16_1920x1080P_60HZ = 16;
        public static int VFMT_CEA_17_720x576P_50HZ = 17;
        public static int VFMT_CEA_18_720x576P_50HZ = 18;
        public static int VFMT_CEA_19_1280x720P_50HZ = 19;
        public static int VFMT_CEA_20_1920x1080I_50HZ = 20;
        public static int VFMT_CEA_21_720x576I_50HZ = 21;
        public static int VFMT_CEA_22_720x576I_50HZ = 22;
        public static int VFMT_CEA_23_720x288P_50HZ = 23;
        public static int VFMT_CEA_24_720x288P_50HZ = 24;
        public static int VFMT_CEA_25_720x576I_50HZ = 25;
        public static int VFMT_CEA_26_720x576I_50HZ = 26;
        public static int VFMT_CEA_27_720x288P_50HZ = 27;
        public static int VFMT_CEA_28_720x288P_50HZ = 28;
        public static int VFMT_CEA_29_1440x576P_50HZ = 29;
        public static int VFMT_CEA_30_1440x576P_50HZ = 30;
        public static int VFMT_CEA_31_1920x1080P_50HZ = 31;
        public static int VFMT_CEA_32_1920x1080P_24HZ = 32;
        public static int VFMT_CEA_33_1920x1080P_25HZ = 33;
        public static int VFMT_CEA_34_1920x1080P_30HZ = 34;
        public static int VFMT_CEA_35_2880x480P_60HZ = 35;
        public static int VFMT_CEA_36_2880x480P_60HZ = 36;
        public static int VFMT_CEA_37_2880x576P_50HZ = 37;
        public static int VFMT_CEA_38_2880x576P_50HZ = 38;
        public static int VFMT_CEA_60_1280x720P_24HZ = 60;
        public static int VFMT_CEA_61_1280x720P_25HZ = 61;
        public static int VFMT_CEA_62_1280x720P_30HZ = 62;
        public static int VFMT_CEA_NULL = 0;
        public static int VFMT_INVALID = 255;
        public static int VFMT_VESA_100_1360X768_60 = 100;
        public static int VFMT_VESA_101_1360X768_120_RB = 101;
        public static int VFMT_VESA_102_1366X768_60 = 102;
        public static int VFMT_VESA_103_1400X1050_60 = 103;
        public static int VFMT_VESA_104_1400X1050_75 = 104;
        public static int VFMT_VESA_105_1400X1050_85 = 105;
        public static int VFMT_VESA_106_1400X1050_120_RB = 106;
        public static int VFMT_VESA_107_1440X900_60_DMT = 107;
        public static int VFMT_VESA_108_1440X900_75 = 108;
        public static int VFMT_VESA_109_1440X900_85 = 109;
        public static int VFMT_VESA_110_1440X900_120_RB = 110;
        public static int VFMT_VESA_111_1600X900_60_CVT = 111;
        public static int VFMT_VESA_112_1600X900_60_DMT_RB = 112;
        public static int VFMT_VESA_113_1600X900_75_CVT = 113;
        public static int VFMT_VESA_114_1600X900_85_CVT = 114;
        public static int VFMT_VESA_115_1600X1200_60 = 115;
        public static int VFMT_VESA_116_1600X1200_70 = 116;
        public static int VFMT_VESA_117_1600X1200_75 = 117;
        public static int VFMT_VESA_118_1600X1200_85 = 118;
        public static int VFMT_VESA_119_1600X1200_120_RB = 119;
        public static int VFMT_VESA_120_1680X1050_60 = 120;
        public static int VFMT_VESA_121_1680X1050_60_RB = 121;
        public static int VFMT_VESA_122_1680X1050_75 = 122;
        public static int VFMT_VESA_123_1680X1050_85 = 123;
        public static int VFMT_VESA_124_1680X1050_120_RB = 124;
        public static int VFMT_VESA_125_1792X1344_60 = 125;
        public static int VFMT_VESA_126_1792X1344_75 = 126;
        public static int VFMT_VESA_127_1856X1392_60 = 127;
        public static int VFMT_VESA_128_1856X1392_75 = 128;
        public static int VFMT_VESA_129_1920X1080_60_DMT = 129;
        public static int VFMT_VESA_130_1920X1080_60_CVT = 130;
        public static int VFMT_VESA_131_1920X1080_60_CVT_RB = 131;
        public static int VFMT_VESA_132_1920X1200_60_CVT = 132;
        public static int VFMT_VESA_133_1920X1200_60_DMT_RB = 133;
        public static int VFMT_VESA_134_1920X1200_75 = 134;
        public static int VFMT_VESA_135_1920X1200_85 = 135;
        public static int VFMT_VESA_136_1920X1440_60 = 136;
        public static int VFMT_VESA_137_1920X1440_75 = 137;
        public static int VFMT_VESA_138_1920X1440_85_CVT = 138;
        public static int VFMT_VESA_139_2048X1536_60_CVT = 139;
        public static int VFMT_VESA_140_2048X1536_75_CVT = 140;
        public static int VFMT_VESA_141_2048X1536_85_CVT = 141;
        public static int VFMT_VESA_142_2560X1600_60 = 142;
        public static int VFMT_VESA_64_640X480_60 = 64;
        public static int VFMT_VESA_65_640X480_75 = 65;
        public static int VFMT_VESA_66_800X600_60 = 66;
        public static int VFMT_VESA_67_800X600_72 = 67;
        public static int VFMT_VESA_68_800X600_75 = 68;
        public static int VFMT_VESA_69_800X600_85 = 69;
        public static int VFMT_VESA_70_800X600_120_RB = 70;
        public static int VFMT_VESA_71_1024X768_60 = 71;
        public static int VFMT_VESA_72_1024X768_70 = 72;
        public static int VFMT_VESA_73_1024X768_75 = 73;
        public static int VFMT_VESA_74_1024X768_85 = 74;
        public static int VFMT_VESA_75_1024X768_120_RB = 75;
        public static int VFMT_VESA_76_1152X864_60 = 76;
        public static int VFMT_VESA_77_1152X864_75 = 77;
        public static int VFMT_VESA_78_1280X600_60 = 78;
        public static int VFMT_VESA_79_1280X720_60_DMT = 79;
        public static int VFMT_VESA_80_1280X720_60_CVT = 80;
        public static int VFMT_VESA_81_1280X720_60_CVT_RB = 81;
        public static int VFMT_VESA_82_1280X720_75_CVT = 82;
        public static int VFMT_VESA_83_1280X720_85_CVT = 83;
        public static int VFMT_VESA_84_1280X768_60 = 84;
        public static int VFMT_VESA_85_1280X768_60_RB = 85;
        public static int VFMT_VESA_86_1280X768_75 = 86;
        public static int VFMT_VESA_87_1280X800_60 = 87;
        public static int VFMT_VESA_88_1280X800_75 = 88;
        public static int VFMT_VESA_89_1280X800_85 = 89;
        public static int VFMT_VESA_90_1280X800_120_RB = 90;
        public static int VFMT_VESA_91_1280X960_60_DMT = 91;
        public static int VFMT_VESA_92_1280X960_60_CVT = 92;
        public static int VFMT_VESA_93_1280X960_75_CVT = 93;
        public static int VFMT_VESA_94_1280X960_85 = 94;
        public static int VFMT_VESA_95_1280X960_120_RB = 95;
        public static int VFMT_VESA_96_1280X1024_60 = 96;
        public static int VFMT_VESA_97_1280X1024_75 = 97;
        public static int VFMT_VESA_98_1280X1024_85 = 98;
        public static int VFMT_VESA_99_1280X1024_120_RB = 99;
    }

    /* loaded from: classes.dex */
    public static class _E_SYNC_POLARITY_ {
        public static int InterVNegHNeg = 0;
        public static int InterVNegHPos = 2;
        public static int InterVPosHNeg = 4;
        public static int InterVPosHPos = 6;
        public static int ProgrVNegHNeg = 1;
        public static int ProgrVNegHPos = 3;
        public static int ProgrVPosHNeg = 5;
        public static int ProgrVPosHPos = 7;
    }

    /* loaded from: classes.dex */
    public static class AS7160_VIDEO_TIMING {
        public int u16_hactive;
        public int u16_hoffset;
        public int u16_hsyncwidth;
        public int u16_htotal;
        public int u16_pixclk;
        public int u16_vactive;
        public int u16_vfreq;
        public int u16_voffset;
        public int u16_vsyncwidth;
        public int u16_vtotal;
        public byte u8_polarity;

        public AS7160_VIDEO_TIMING(byte b, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            this.u8_polarity = b;
            this.u16_htotal = i;
            this.u16_vtotal = i2;
            this.u16_hactive = i3;
            this.u16_vactive = i4;
            this.u16_pixclk = i5;
            this.u16_vfreq = i6;
            this.u16_hoffset = i7;
            this.u16_voffset = i8;
            this.u16_hsyncwidth = i9;
            this.u16_vsyncwidth = i10;
        }
    }

    /* loaded from: classes.dex */
    public static class Select_tim {
        public int map;
        public String pstr;

        public Select_tim(int i, String str) {
            this.map = i;
            this.pstr = str;
        }
    }

    /* loaded from: classes.dex */
    public static class AS7160_MISC_TIMING {
        public AS7160_VIDEO_TIMING st_timing;
        public byte u8_vic;

        public AS7160_MISC_TIMING(byte b, AS7160_VIDEO_TIMING as7160_video_timing) {
            this.u8_vic = b;
            this.st_timing = as7160_video_timing;
        }
    }

    /* loaded from: classes.dex */
    public static class AS7160_SIMPLE_TIMING {
        public int u16_hactive;
        public int u16_pixclk;
        public int u16_vactive;
        public int u8_framerate;
        public int u8_vic;

        public AS7160_SIMPLE_TIMING(int i, int i2, int i3, int i4, int i5) {
            this.u8_vic = i;
            this.u16_hactive = i2;
            this.u16_vactive = i3;
            this.u16_pixclk = i4;
            this.u8_framerate = i5;
        }
    }

    public static byte getVIC(int i, RESOLUTION resolution) {
        byte b = (byte) _E_AS7160_VIDEO_FORMAT_.VFMT_INVALID;
        int i2 = 0;
        while (true) {
            AS7160_SIMPLE_TIMING[] as7160_simple_timingArr = g_arrSimpleTimingTable;
            if (i2 >= as7160_simple_timingArr.length) {
                return b;
            }
            AS7160_SIMPLE_TIMING as7160_simple_timing = as7160_simple_timingArr[i2];
            if (resolution.width == as7160_simple_timing.u16_hactive && resolution.height == as7160_simple_timing.u16_vactive && Math.abs(resolution.framerate - as7160_simple_timing.u8_framerate) <= 2) {
                return (byte) as7160_simple_timing.u8_vic;
            }
            i2++;
        }
    }

    public static RESOLUTION getResolution(byte b) {
        int i = 0;
        while (true) {
            AS7160_SIMPLE_TIMING[] as7160_simple_timingArr = g_arrSimpleTimingTable;
            RESOLUTION resolution = null;
            if (i >= as7160_simple_timingArr.length) {
                break;
            }
            AS7160_SIMPLE_TIMING as7160_simple_timing = as7160_simple_timingArr[i];
            if (((byte) as7160_simple_timing.u8_vic) == b) {
                resolution.width = as7160_simple_timing.u16_hactive;
                resolution.height = as7160_simple_timing.u16_vactive;
                resolution.framerate = as7160_simple_timing.u8_framerate;
                break;
            }
            i++;
        }
        return null;
    }

    /* loaded from: classes.dex */
    public static class DataType {

        /* loaded from: classes.dex */
        public static class COLORSPACE {
            public static int RGB565 = 0;
            public static int RGB888 = 1;
            public static int YUV422 = 2;
            public static int YUV444 = 3;
        }

        /* loaded from: classes.dex */
        public static class CONNECT_STATE {
            public static int Both = 3;
            public static int None = 0;
            public static int OnlyHid = 1;
            public static int OnlyVideo = 2;
        }

        /* loaded from: classes.dex */
        public static class SDRAM_TYPE {
            public static int SDRAM_16M = 3;
            public static int SDRAM_2M = 0;
            public static int SDRAM_4M = 1;
            public static int SDRAM_8M = 2;
            public static int SDRAM_NONE = 4;
        }

        /* loaded from: classes.dex */
        public static class TRANSFER_MODE {
            public static int FIX_BLOCK_MN = 1;
            public static int FIX_BLOCK_WH = 2;
            public static int FRAME = 0;
            public static int MANUAL_BLOCK = 3;
            public static int MEM_BYPAS_BLOCK = 5;
            public static int MEM_BYPAS_FRAME = 4;
        }

        /* loaded from: classes.dex */
        public static class VIDEO_PORT {
            public static int INVALID = 100;
            public static int VIDEO_OUTPUT_PORT_CVBS = 0;
            public static int VIDEO_OUTPUT_PORT_CVBS_SVIDEO = 4;
            public static int VIDEO_OUTPUT_PORT_DIGITAL = 6;
            public static int VIDEO_OUTPUT_PORT_HDMI = 5;
            public static int VIDEO_OUTPUT_PORT_SVIDEO = 1;
            public static int VIDEO_OUTPUT_PORT_VGA = 2;
            public static int VIDEO_OUTPUT_PORT_YPBPR = 3;
        }

        /* loaded from: classes.dex */
        public enum blockSettingMethod {
            blockMN,
            blockWidthHeight
        }

        /* loaded from: classes.dex */
        public static class packingMethod {
            public static int fix_block_size = 0;
            public static int manu_block_size = 1;
        }

        /* loaded from: classes.dex */
        public class DATA_BUFFER {
            public byte[] dataBuffer;
            public boolean isReadable;
            public boolean isWriteable;

            public DATA_BUFFER() {
            }
        }
    }

    /* loaded from: classes.dex */
    public class RESOLUTION {
        public int framerate;
        public int height;
        public int width;

        public RESOLUTION() {
        }
    }
}
