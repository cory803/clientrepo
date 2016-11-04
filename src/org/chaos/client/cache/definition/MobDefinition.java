package org.chaos.client.cache.definition;

import org.chaos.Configuration;
import org.chaos.client.Client;
import org.chaos.client.FrameReader;
import org.chaos.client.List;
import org.chaos.client.cache.Archive;
import org.chaos.client.io.ByteBuffer;
import org.chaos.client.world.Model;

public final class MobDefinition {

    private static MobDefinition[] cache;
    private static int cacheIndex;
    public static Client clientInstance;
    public static List mruNodes = new List(30);
    private static ByteBuffer buffer;
    private static ByteBuffer osrsBuffer;
    private static int[] streamIndices;
    private static int[] osrsStreamIndices;

    public static int[] osrsNpcs = {637, 635, 401, 402, 403, 404, 405, 6618, 6619, 6620, 5779, 6618, 5535, 491, 492, 493, 496, 6611, 2054, 5866, 5886, 388, 2042, 6593, 497, 6609, 964, 5547, 6656, 2127, 2129, 2128, 6626, 6627, 6641, 6643, 6644, 6646, 6647, 6652, 5907, 6653, 6655, 5536, 495, 5892, 6717, 6715, 6716};

    public static MobDefinition get(int id) {
        for (int i = 0; i < 20; i++) {
            if (cache[i].id == id) {
                return cache[i];
            }
        }
        cacheIndex = (cacheIndex + 1) % 20;
        MobDefinition definition = cache[cacheIndex] = new MobDefinition();
        buffer.position = streamIndices[id];
        boolean osrs = false;
        for (int i = 0; i < osrsNpcs.length; i++) {
            if(osrsNpcs[i] == id) {
                osrsBuffer.position = osrsStreamIndices[id];
                if(id == 494) {
                    buffer.position = streamIndices[id];
                }
                osrs = true;
            }
        }
        definition.id = id;
        if(osrs) {
            definition.readValues(osrsBuffer);
        } else {
            definition.readValues(buffer);
        }
        switch (id) {
            case 3368: //smaller size fishing spot
                definition.copy(get(318));
                definition.adjustVertextPointsXOrY = 105;
                definition.drawYellowDotOnMap = false;
                break;
            case 1304:
                definition.actions = new String[] {"Talk-to", null, "Travel-with", null, null};
                break;
            case 6644: //Correcting animations for Bandos Pet
                definition.standAnimation = 7059;
                definition.walkAnimation = 7058;
                break;
            case 6647: //Correcting animations for Zamorak Pet
                definition.standAnimation = 6943;
                definition.walkAnimation = 6942;
                break;
            case 6717: //Correcting animations for Beaver Pet (Woodcutting)
                definition.walkAnimation = 7719;
                definition.standAnimation = 9952;
                break;
            case 9713:
                definition.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 502: //Kraken boss
                definition.npcModels = new int[1];
                definition.npcModels[0] = 28231;
                definition.name = "Kraken";
                definition.actions = new String[] {null, "Attack", null, null, null};
                definition.standAnimation = 3989;
                definition.walkAnimation = 3989;
                definition.combatLevel = 291;
                definition.npcSizeInSquares = 6;
                definition.adjustVertextPointsXOrY = 150;
                definition.adjustVertextPointZ = 150;
                definition.modelLightning = 30;
                definition.modelShadowing = 150;
                break;
            case 6362:
                definition.name = "Banker";
                break;
            case 553:
                definition.copy(get(637));
                break;
            case 6247:
                definition.npcModels = get(6646).npcModels;
                break;
            case 151481:
                System.out.println("----");
                System.out.println("name: "+definition.name);
                //for (int i = 0; i < definition.npcModels.length; i++) {
                //    System.out.println("Model "+i+": "+definition.npcModels[i]);
                //}
                //System.out.println("Size: "+definition.npcSizeInSquares);
               // System.out.println("Stand animation: "+definition.standAnimation);
                //System.out.println("Walk animation: "+definition.walkAnimation);
                //System.out.println("Size: "+definition.npcSizeInSquares);
                for (int i = 0; i < definition.dialogueModels.length; i++) {
                    System.out.println("Dialogue Model "+i+": "+definition.dialogueModels[i]);
                }
                break;
            case 402:
                definition.dialogueModels[0] = 76500;
                break;
            case 490:
                definition.name = "Nieve";
                definition.walkAnimation = 1205;
                definition.standAnimation = 813;
                definition.npcModels = new int[10];
                definition.npcModels[0] = 392; // Hat
                definition.npcModels[1] = 27644; // Platebody
                definition.npcModels[8] = 27640; // Platebody arms
                definition.npcModels[2] = 19951; // Platelegs
                definition.npcModels[3] = 3661; // Cape
                definition.npcModels[4] = 28827; // Gloves
                definition.npcModels[5] = 9644; // Boots
                definition.npcModels[6] = 27654; // Amulet
                definition.npcModels[7] = 9640; // Fire cape
                definition.npcModels[9] = 40942; // Elysian spirit shield
                definition.dialogueModels = new int[] {10031};
                definition.actions = new String[] {"Talk-to", "Assignment", "Trade", "Rewards", null};
                definition.combatLevel = 0;
                definition.npcSizeInSquares = 1;
                definition.adjustVertextPointsXOrY = 128;
                definition.adjustVertextPointZ = 128;
                definition.originalModelColours = new int[] {6798};
                definition.changedModelColours = new int[] {9137};
                break;
            case 5428:
                definition.name = "Agility Penguin";
                definition.actions = new String[]{"Talk-to", null, "Trade", "Travel", null};
                definition.adjustVertextPointsXOrY = 135;
                definition.adjustVertextPointZ = 135;
                for (int i = 0; i < definition.dialogueModels.length; i++) {
                    System.out.println("Dialogue Model "+i+": "+definition.dialogueModels[i]);
                }
                break;
            case 5886: //TODO: Add abyssal sire models
            case 5866:
                definition.actions = new String[5];
                definition.actions[1] = "Attack";
            break;
            case 247:
                definition.actions = new String[]{"Talk-to", null, null, null, null};
                break;
            case 2691:
                definition.name = "Alvin";
                break;
            case 321:
                definition.copy(get(635));
                definition.name = "Rocktail";
                definition.description = "The wilderness rocktail fishing spot.".getBytes();
                definition.actions = new String[]{"Fish", null, null, null, null};
                break;
            case 322:
                definition.copy(get(635));
                definition.name = "Karambwan";
                definition.description = "The wilderness karambwan fishing spot.".getBytes();
                definition.actions = new String[]{"Net", null, null, null, null};
                break;
            case 13727:
                definition.npcModels = new int[]{8377};
                definition.name = "Xuan";
                definition.description = "The most loyal person you'll ever know.".getBytes();
                definition.standAnimation = 808;
                definition.walkAnimation = 819;
                definition.actions = new String[]{"Talk-to", null, "Open-shop", "Clear-title", null};
                definition.dialogueModels = new int[]{8324};
                break;
            case 1851:
            case 1854:
            case 1857:
                definition.combatLevel = 0;
                break;
            case 5871:
                definition.name = "Bork";
                definition.npcModels = new int[]{32351, 32352};
                definition.npcSizeInSquares = 3;
                definition.actions = new String[]{null, "Attack", null, null, null};
                definition.standAnimation = 8753;
                definition.walkAnimation = 8752;
                definition.drawYellowDotOnMap = true;
                definition.combatLevel = 841;
                definition.adjustVertextPointsXOrY = 128;
                definition.adjustVertextPointZ = 128;
                break;
            case 437:
                definition.actions = new String[]{"Talk-to", null, null, null, null};
                break;
            case 2722:
                MobDefinition spot1 = get(318);
                definition.npcSizeInSquares = spot1.npcSizeInSquares;
                definition.adjustVertextPointsXOrY = spot1.adjustVertextPointsXOrY;
                definition.adjustVertextPointZ = spot1.adjustVertextPointZ;
                definition.actions = new String[]{"Net", null, null, null, null};
                break;
            case 2724:
                MobDefinition spot2 = get(318);
                definition.npcSizeInSquares = spot2.npcSizeInSquares;
                definition.adjustVertextPointsXOrY = spot2.adjustVertextPointsXOrY;
                definition.adjustVertextPointZ = spot2.adjustVertextPointZ;
                definition.actions = new String[]{"Bait", null, null, null, null};
                break;
            case 130:
                definition.name = "Ganodermic beast";
                definition.combatLevel = 280;
                definition.npcSizeInSquares = 3;
                definition.drawYellowDotOnMap = true;
                definition.adjustVertextPointsXOrY = 128;
                definition.adjustVertextPointZ = 128;
                definition.npcModels = new int[]{13888};
                definition.actions = new String[]{null, "Attack", null, null, null};
                 definition.degreesToTurn = 32;
                definition.standAnimation = 4553;
                definition.walkAnimation = 4555;
                break;
            case 501:
                definition.name = "Queen Revenant";
                break;
            case 3019:
                MobDefinition spot3 = get(318);
                definition.npcSizeInSquares = spot3.npcSizeInSquares;
                definition.adjustVertextPointsXOrY = spot3.adjustVertextPointsXOrY;
                definition.adjustVertextPointZ = spot3.adjustVertextPointZ;
                definition.actions = new String[]{"Net", null, null, null, null};
                break;
            case 5867:
                definition.name = "Summoned Soul";
                definition.npcModels = new int[]{29268};
                definition.npcSizeInSquares = 1;
                definition.actions = new String[]{null, null, null, null, null};
                definition.standAnimation = 4505;
                definition.walkAnimation = 4505;
                definition.drawYellowDotOnMap = true;
                definition.combatLevel = 96;
                definition.adjustVertextPointsXOrY = 140;
                definition.adjustVertextPointZ = 140;
                break;
            case 5868:
                definition.name = "Summoned Soul";
                definition.npcModels = new int[]{29267};
                definition.npcSizeInSquares = 1;
                definition.actions = new String[]{null, null, null, null, null};
                definition.standAnimation = 4505;
                definition.walkAnimation = 4505;
                definition.drawYellowDotOnMap = true;
                definition.combatLevel = 96;
                definition.adjustVertextPointsXOrY = 140;
                definition.adjustVertextPointZ = 140;
                break;

            case 5869:
                definition.name = "Summoned Soul";
                definition.npcModels = new int[]{29266};
                definition.npcSizeInSquares = 1;
                definition.actions = new String[]{null, null, null, null, null};
                definition.standAnimation = 4505;
                definition.walkAnimation = 4505;
                definition.drawYellowDotOnMap = true;
                definition.combatLevel = 79;
                definition.adjustVertextPointsXOrY = 140;
                definition.adjustVertextPointZ = 140;
                break;
            case 2000:
                definition.npcModels = new int[2];
                definition.npcModels[0] = 28294;
                definition.npcModels[1] = 28295;
                definition.name = "Venenatis";
                definition.actions = new String[]{null, "Attack", null, null, null};
                definition.adjustVertextPointsXOrY = 200;
                definition.adjustVertextPointZ = 200;
                MobDefinition ven = get(60);
                definition.standAnimation = ven.standAnimation;
                definition.walkAnimation = ven.walkAnimation;
                definition.combatLevel = 464;
                definition.npcSizeInSquares = 5;
                break;
            case 2593:
                definition.name = "Merchant trader";
                definition.actions = new String[]{"Talk-To", null, "Search through stores", "Open your store", "Open someone's store"};
               definition.standAnimation = 808;
                break;
            case 2001:
                definition.npcModels = new int[1];
                definition.npcModels[0] = 28293;
                definition.name = "Scorpia";
                definition.actions = new String[]{null, "Attack", null, null, null};
                MobDefinition scor = get(107);
                definition.standAnimation = scor.standAnimation;
                definition.walkAnimation = scor.walkAnimation;
                definition.combatLevel = 464;
                definition.npcSizeInSquares = 3;
                break;
            case 6139:
                definition.actions = new String[]{"Talk-to", null, "Change Home", null, null};
                break;
            case 1396:
                definition.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 4646:
                definition.actions = new String[]{"Talk-to", null, "Teleport", null, null};
                break;
            case 4663:
                definition.actions = new String[]{"Talk-to", null, null, null, null};
                break;
            case 2947:
                definition.actions = new String[]{"Pickpocket", null, null, null, null};
                break;
            case 4002:
                definition.copy(get(2633));
                break;
            case 457:
                definition.name = "Ghost Town Citizen";
                definition.actions = new String[]{"Talk-to", null, "Teleport", null, null};
                break;
            case 198:
                definition.name = "Master Chief";
                definition.actions = new String[]{"Talk-to", null, "Spawn Last Boss", null, null};
                break;
            case 1093:
                definition.name = "Billy the Goat";
                definition.actions = new String[]{"Talk-to", null, null, null, null};
                break;
            case 5417:
                definition.combatLevel = 210;
                break;
            case 5418:
                definition.combatLevel = 90;
                break;
            case 6701:
                definition.combatLevel = 173;
                break;
            case 6725:
                definition.combatLevel = 224;
                break;
            case 6691:
                definition.npcSizeInSquares = 2;
                definition.combatLevel = 301;
                break;
            case 8710:
            case 8707:
            case 8706:
            case 8705:
                definition.name = "Musician";
                definition.actions = new String[]{"Listen-to", null, null, null, null};
                break;
            case 9939:
                definition.combatLevel = 607;
                break;
            case 688:
                definition.name = "Archer";
                break;
            case 4540:
                definition.combatLevel = 299;
                break;
            case 2998:
                definition.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 3101:
                definition.adjustVertextPointZ = definition.adjustVertextPointsXOrY = 80;
                definition.npcSizeInSquares = 1;
                definition.actions = new String[]{"Talk-to", null, "Start", "Rewards", null};
                break;
            case 6222:
                definition.name = "Kree'arra";
                definition.npcSizeInSquares = 5;
                definition.standAnimation = 6972;
                definition.walkAnimation = 6973;
                definition.actions = new String[]{null, "Attack", null, null, null};
                definition.adjustVertextPointZ = definition.adjustVertextPointsXOrY = 110;
                break;
            case 6203:
                definition.npcModels = new int[]{27768, 27773, 27764, 27765, 27770};
                definition.name = "K'ril Tsutsaroth";
                definition.npcSizeInSquares = 5;
                definition.standAnimation = 6943;
                definition.walkAnimation = 6942;
                definition.actions = new String[]{null, "Attack", null, null, null};
                definition.adjustVertextPointZ = definition.adjustVertextPointsXOrY = 110;
                break;
            case 1610:
            case 491:
            case 10216:
                definition.actions = new String[]{null, "Attack", null, null, null};
                break;
            case 817:
            case 7969:
                definition.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 5110:
                definition.actions = new String[]{"Talk-to", null, "Trade", "Travel-with", null};
                break;
            case 805:
                definition.actions = new String[]{"Talk-to", null, "Tan-hides", null, null};
                break;
            case 308:
                definition.name = "Martin";
                definition.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 1382:
                definition.name = "Glacor";
                definition.npcModels = new int[]{58940};
                definition.npcSizeInSquares = 3;
                // definition.anInt86 = 475;
                definition.adjustVertextPointsXOrY = definition.adjustVertextPointZ = 180;
                definition.standAnimation = 10869;
                definition.walkAnimation = 10867;
                definition.actions = new String[]{null, "Attack", null, null, null};
                definition.combatLevel = 123;
                definition.drawYellowDotOnMap = true;
                definition.combatLevel = 188;
                break;
            case 4249:
                definition.name = "Gambler";
                break;
            case 4657:
                definition.actions = new String[]{"Talk-to", null, "Claim Items", "Check Total", "Teleport"};
                break;
            case 605:
                definition.actions = new String[]{"Talk-to", null, "Vote Rewards", "Loyalty Titles", null};
                break;
            case 8591:
                definition.actions = new String[]{"Talk-to", null, "Trade", null, null};
                break;
            case 316:
            case 315:
            case 310:
            case 314:
            case 312:
            case 313:
                definition.copyFishing(get(635));
                break;
            case 309:
                definition.name = "Rocky shoal";
                definition.actions = new String[]{"Bait", null, null, null, null};
                definition.copyFishing(get(635));
                break;
            case 318:
                definition.copyFishing(get(635));
                definition.actions = new String[]{"Net", null, "Lure", null, null};
                break;
            case 8022:
            case 8028:
                String color = id == 8022 ? "Yellow" : "Green";
                definition.name = "" + color + " energy source";
                definition.actions = new String[]{"Siphon", null, null, null, null};
                break;
            case 8444:
                definition.actions = new String[5];
                definition.actions[0] = "Trade";
                break;
            case 2579:
                definition.name = "Max";
                definition.description = "He's mastered the many skills on Chaos.".getBytes();
                definition.combatLevel = 1337;
                definition.actions = new String[5];
                definition.actions[0] = "Talk-to";
                definition.standAnimation = 808;
                definition.walkAnimation = 819;
                definition.npcModels = new int[]{65291, 65322, 506, 529, 252, 9642, 62746, 13307, 62743, 53327};
                definition.dialogueModels = new int[]{39332, 39235};
                break;
            case 4519:
                definition.name = "Banker";
                definition.description = "A banker of many dangerous values.".getBytes();
                definition.combatLevel = 0;
                definition.actions = new String[5];
                definition.actions[0] = "Talk-to";
                definition.actions[2] = "Bank";
                definition.standAnimation = 808;
                definition.walkAnimation = 819;
                definition.npcModels = new int[9];
                definition.npcModels[0] = 3188; // Hat
                definition.npcModels[1] = 19258; // Platebody
                definition.npcModels[8] = 40731; // Platebody arms
                definition.npcModels[2] = 5024; // Platelegs
                definition.npcModels[3] = 7122; // Cape
                definition.npcModels[4] = 13307; // Gloves
                definition.npcModels[5] = 27738; // Boots
                definition.npcModels[6] = 9642; // Amulet
                definition.npcModels[7] = 43660; // Weapon
                definition.dialogueModels = MobDefinition.get(494).dialogueModels;
                break;
            case 595:
                definition.name = "Ellis";
                definition.description = "Hmm... I wonder how he got here?".getBytes();
                break;
            case 572:
                definition.name = "Barrows brother";
                definition.description = "A brother of great historic resemblance.".getBytes();
                definition.actions = new String[] {"Talk-to", null, null, null, null};
                break;
            case 4252:
                definition.name = "Donovan";
                definition.description = "Gunther, The Chaos donation master.".getBytes();
                definition.combatLevel = 0;
                definition.actions = new String[5];
                definition.actions[0] = "Talk-to";
                definition.standAnimation = 808;
                definition.walkAnimation = 819;
                definition.npcModels = new int[8];
                definition.npcModels[0] = ItemDefinition.get(1057).maleWearId; // Hat
                definition.npcModels[1] = ItemDefinition.get(15040).maleWearId; // Platebody
                definition.npcModels[2] = ItemDefinition.get(15042).maleWearId; // Platelegs
                definition.npcModels[3] = ItemDefinition.get(14022).maleWearId; // Cape
                definition.npcModels[4] = ItemDefinition.get(15044).maleWearId; // Gloves
                definition.npcModels[5] = ItemDefinition.get(15043).maleWearId; // Boots
                definition.npcModels[6] = ItemDefinition.get(19335).maleWearId; // Amulet
                //definition.npcModels[7] = ItemDefinition.get(15040).maleWearId2; // Arms #2
                definition.npcModels[7] = ItemDefinition.get(15449).maleWearId; // Shield

                definition.adjustVertextPointsXOrY = 142;
                definition.adjustVertextPointZ = 142;
                definition.dialogueModels = new int[] {ItemDefinition.get(1057).maleWearId};

                definition.changedModelColours = new int[] { 788, 786, 786, 786, 783, 778, 1, 788, 786, 1, 947};
                definition.originalModelColours = new int[] { 43306, 43302, 47530, 41, 47532, 47528, 0, 31762, 47512, 0, 22439 };
                break;
            case 6830:
            case 6841:
            case 6796:
            case 7331:
            case 6831:
            case 7361:
            case 6847:
            case 6872:
            case 7353:
            case 6835:
            case 6845:
            case 7370:
            case 7333:
            case 7351:
            case 7367:
            case 6853:
            case 6855:
            case 6857:
            case 6859:
            case 6861:
            case 6863:
            case 9481:
            case 6827:
            case 6889:
            case 6813:
            case 6817:
            case 7372:
            case 6839:
            case 8575:
            case 7345:
            case 6799:
            case 7335:
            case 7347:
            case 6800:
            case 9488:
            case 6804:
            case 6822:
            case 6849:
            case 7355:
            case 7357:
            case 7359:
            case 7341:
            case 7329:
            case 7339:
            case 7349:
            case 7375:
            case 7343:
            case 6820:
            case 6865:
            case 6809:
            case 7363:
            case 7337:
            case 7365:
            case 6991:
            case 6992:
            case 6869:
            case 6818:
            case 6843:
            case 6823:
            case 7377:
            case 6887:
            case 6885:
            case 6883:
            case 6881:
            case 6879:
            case 6877:
            case 6875:
            case 6833:
            case 6851:
            case 5079:
            case 5080:
            case 6824:
                definition.actions = new String[]{null, null, null, null, null};
                break;
            case 6806: // thorny snail
            case 6807:
            case 6994: // spirit kalphite
            case 6995:
            case 6867: // bull ant
            case 6868:
            case 6794: // spirit terrorbird
            case 6795:
            case 6815: // war tortoise
            case 6816:
                // case 6874:// pack yak
                // case 6873: // pack yak
            case 3594: // yak
            case 3590: // war tortoise
            case 3596: // terrorbird
                definition.actions = new String[]{"Store", null, null, null, null};
                break;
            case 1265:
            case 1267:
            case 8459:
                definition.drawYellowDotOnMap = true;
                break;
            case 6537:
                definition.actions = new String[]{null, null, "Talk-to", "Sell-Artifacts", null};
                break;
            case 2253:
                definition.actions = new String[]{"Talk-to", null, null, null, null};
                break;
            case 291:
                definition.actions = new String[]{"Pickpocket", null, null, null, null};
                break;
            case 2676:
                definition.actions = new String[]{"Makeover", null, null, null, null};
                break;
            case 1360:
                definition.actions = new String[]{"Talk-to", "Bank", null, null, null};
                break;
            case 494:
                definition.walkAnimation = 819;
                definition.actions = new String[]{"Talk-to", null, null, null, null};
                break;
            case 747:
                definition.actions = new String[]{"Talk-to", null, "Sell-Artifacts", null, null};
                definition.walkAnimation = 819;
                break;

            case 4906:
                definition.name = "Wilfred";
                definition.actions = new String[5];
                definition.actions = new String[]{"Trade with", null, "Exchange-Kindling", null, null};
                definition.description = "A master woodcutter.".getBytes();
                break;
            case 6873:
            case 6874:
                definition.standAnimation = 5785;
                definition.walkAnimation = 5781;
                definition.npcModels[0] = 23892;
                // 23892
                break;
            case 5529:
                definition.standAnimation = 5785;
                definition.walkAnimation = 5781;
                // 23892
                break;
            case 945:
                definition.name = "Chaos Guide";
                break;
            case 5030:
                definition.actions[2] = "Travel-with";
                break;

            case 548:
            case 520:
            case 2538:
                definition.actions = new String[] {"Talk-to", null, "Trade", null, null};
                break;
        }
        return definition;
    }

    public void copy(MobDefinition other) {
        name = other.name;
        npcModels = other.npcModels;
        npcSizeInSquares = other.npcSizeInSquares;
        adjustVertextPointsXOrY = other.adjustVertextPointsXOrY;
        standAnimation = other.standAnimation;
        walkAnimation = other.walkAnimation;
        actions = other.actions;
        combatLevel = other.combatLevel;
        adjustVertextPointZ = other.adjustVertextPointZ;
        visibilityOrRendering = other.visibilityOrRendering;
        dialogueModels = other.dialogueModels;
    }

    public void copyFishing(MobDefinition other) {
        name = other.name;
        npcModels = other.npcModels;
        npcSizeInSquares = other.npcSizeInSquares;
        adjustVertextPointsXOrY = other.adjustVertextPointsXOrY;
        standAnimation = other.standAnimation;
        walkAnimation = other.walkAnimation;
        combatLevel = other.combatLevel;
        adjustVertextPointZ = other.adjustVertextPointZ;
        visibilityOrRendering = other.visibilityOrRendering;
        dialogueModels = other.dialogueModels;
    }

    public void copy(int id) {
        MobDefinition other = get(id);
        changedModelColours = other.changedModelColours.clone();
        childrenIDs = other.childrenIDs.clone();
        combatLevel = other.combatLevel;
        configChild = other.configChild;
        degreesToTurn = other.degreesToTurn;
        description = other.description;
        dialogueModels = other.dialogueModels;
        disableRightClick = false;
        drawYellowDotOnMap = other.drawYellowDotOnMap;
        headIcon = other.headIcon;
        modelLightning = other.modelLightning;
        modelShadowing = other.modelShadowing;
        npcModels = other.npcModels.clone();
        originalModelColours = other.originalModelColours.clone();
        standAnimation = other.standAnimation;
        varBitChild = other.varBitChild;
        visibilityOrRendering = other.visibilityOrRendering;
        walkAnimation = other.walkAnimation;
        walkingBackwardsAnimation = other.walkingBackwardsAnimation;
        walkLeftAnimation = other.walkLeftAnimation;
        walkRightAnimation = other.walkRightAnimation;
    }

    public static void nullify() {
        mruNodes = null;
        streamIndices = null;
        osrsStreamIndices = null;
        cache = null;
        buffer = null;
        osrsBuffer = null;
    }

    public static int totalNpcs = 0;

    public static void load(Archive archive) {
        buffer = new ByteBuffer(archive.get("npc.dat"));
        ByteBuffer stream2 = new ByteBuffer(archive.get("npc.idx"));
        osrsBuffer = new ByteBuffer(archive.get("osrsnpc.dat"));
        ByteBuffer osrsStream2 = new ByteBuffer(archive.get("osrsnpc.idx"));
        int totalNPCs = stream2.getUnsignedShort();
        int osrsTotalNPCs = osrsStream2.getUnsignedShort();
        totalNpcs = totalNPCs;
        streamIndices = new int[totalNPCs];
        osrsStreamIndices = new int[osrsTotalNPCs];

        int position = 2;

        for (int i = 0; i < totalNPCs; i++) {
            streamIndices[i] = position;
            position += stream2.getUnsignedShort();
        }

        int osrsPosition = 2;

        for (int i = 0; i < osrsTotalNPCs; i++) {
            osrsStreamIndices[i] = osrsPosition;
            osrsPosition += osrsStream2.getUnsignedShort();
        }

        cache = new MobDefinition[20];

        for (int i = 0; i < 20; i++) {
            cache[i] = new MobDefinition();
        }
    }

    public String[] actions;
    private int adjustVertextPointsXOrY;
    private int adjustVertextPointZ;
    private int[] changedModelColours;
    public int[] childrenIDs;
    public int combatLevel;
    private int configChild;
    public int degreesToTurn;
    public byte[] description;
    private int[] dialogueModels;
    public boolean disableRightClick;
    public boolean drawYellowDotOnMap;
    public int headIcon;
    private int modelLightning;
    private int modelShadowing;
    public String name;
    public int[] npcModels;
    public byte npcSizeInSquares;
    private int[] originalModelColours;
    public int standAnimation;
    public int id;
    private int varBitChild;
    public boolean visibilityOrRendering;
    public int walkAnimation;
    public int walkingBackwardsAnimation;
    public int walkLeftAnimation;
    public int walkRightAnimation;

    private MobDefinition() {
        walkRightAnimation = -1;
        varBitChild = -1;
        walkingBackwardsAnimation = -1;
        configChild = -1;
        combatLevel = -1;
        walkAnimation = -1;
        npcSizeInSquares = 1;
        headIcon = -1;
        standAnimation = -1;
        id = -1;
        degreesToTurn = 32;
        walkLeftAnimation = -1;
        disableRightClick = true;
        adjustVertextPointZ = 128;
        drawYellowDotOnMap = true;
        adjustVertextPointsXOrY = 128;
        visibilityOrRendering = false;
    }

    public Model method160() {
        if (childrenIDs != null) {
            MobDefinition definition = method161();

            if (definition == null) {
                return null;
            } else {
                return definition.method160();
            }
        }

        if (dialogueModels == null) {
            return null;
        }

        boolean flag1 = false;

        for (int i = 0; i < dialogueModels.length; i++) {
            if (!Model.isModelLoaded(dialogueModels[i])) {
                flag1 = true;
            }
        }

        if (flag1) {
            return null;
        }

        Model aclass30_sub2_sub4_sub6s[] = new Model[dialogueModels.length];

        for (int j = 0; j < dialogueModels.length; j++) {
            aclass30_sub2_sub4_sub6s[j] = Model.fetchModel(dialogueModels[j]);
        }

        Model model;

        if (aclass30_sub2_sub4_sub6s.length == 1) {
            model = aclass30_sub2_sub4_sub6s[0];
        } else {
            model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
        }

        if (originalModelColours != null) {
            for (int k = 0; k < originalModelColours.length; k++) {
                model.method476(originalModelColours[k], changedModelColours[k]);
            }
        }

        return model;
    }

    public MobDefinition method161() {
        int j = -1;

        try {
            if (varBitChild != -1) {
                VarBit varBit = VarBit.cache[varBitChild];
                int k = varBit.configId;
                int l = varBit.configValue;
                int i1 = varBit.anInt650;
                int j1 = Client.anIntArray1232[i1 - l];
                // System.out.println("k: " + k + " l: " + l);
                j = clientInstance.variousSettings[k] >> l & j1;
            } else if (configChild != -1) {
                j = clientInstance.variousSettings[configChild];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1) {
            return null;
        } else {
            return get(childrenIDs[j]);
        }
    }

    public Model method164(int j, int frame, int ai[], int nextFrame, int cycle1, int cycle2) {
        if (childrenIDs != null) {
            MobDefinition entityDef = method161();

            if (entityDef == null) {
                return null;
            } else {
                return entityDef.method164(j, frame, ai, nextFrame, cycle1, cycle2);
            }
        }

        Model model = (Model) mruNodes.insertFromCache(id);

        if (model == null) {
            boolean flag = false;

            for (int i1 = 0; i1 < npcModels.length; i1++) {
                if (!Model.isModelLoaded(npcModels[i1])) {
                    flag = true;
                }
            }

            if (flag) {
                return null;
            }

            Model aclass30_sub2_sub4_sub6s[] = new Model[npcModels.length];

            for (int j1 = 0; j1 < npcModels.length; j1++) {
                aclass30_sub2_sub4_sub6s[j1] = Model.fetchModel(npcModels[j1]);
            }

            if (aclass30_sub2_sub4_sub6s.length == 1) {
                model = aclass30_sub2_sub4_sub6s[0];
            } else {
                model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
            }

            if (originalModelColours != null) {
                for (int k1 = 0; k1 < originalModelColours.length; k1++) {
                    model.method476(originalModelColours[k1], changedModelColours[k1]);
                }
            }

            model.method469();
            model.method479(84 + modelLightning, 1000 + modelShadowing, -90, -580, -90, true);
            mruNodes.removeFromCache(model, id);
        }

        Model model_1 = Model.aModel_1621;
        model_1.method464(model, FrameReader.isNullFrame(frame) & FrameReader.isNullFrame(j));
        /*
		 * if (frame != -1 && j != -1) { model_1.method471(ai, j, frame); } else
		 * if (frame != -1 && !Configuration.TWEENING_ENABLED) {
		 * model_1.applyTransform(frame); } else if (frame != -1 && nextFrame !=
		 * -1 && Configuration.TWEENING_ENABLED) {
		 * model_1.interpolateFrames(frame, nextFrame, cycle1, cycle2); }
		 */

        if (frame != -1 && j != -1)
            model_1.method471(ai, j, frame);
        else if (frame != -1 && nextFrame != -1 && Configuration.TWEENING_ENABLED)
            model_1.interpolateFrames(frame, nextFrame, cycle1, cycle2);
        else if (frame != -1)
            model_1.applyTransform(frame);

        if (adjustVertextPointsXOrY != 128 || adjustVertextPointZ != 128) {
            model_1.method478(adjustVertextPointsXOrY, adjustVertextPointsXOrY, adjustVertextPointZ);
        }

        model_1.method466();
        model_1.anIntArrayArray1658 = null;
        model_1.anIntArrayArray1657 = null;

        if (npcSizeInSquares == 1) {
            model_1.aBoolean1659 = true;
        }

        return model_1;
    }

    private void readValues(ByteBuffer buffer) {
        do {
            final int opcode = buffer.getUnsignedByte();

            if (opcode == 0) {
                return;
            }

            if (opcode == 1) {
                int j = buffer.getUnsignedByte();
                npcModels = new int[j];

                for (int j1 = 0; j1 < j; j1++) {
                    npcModels[j1] = buffer.getUnsignedShort();
                }
            } else if (opcode == 2) {
                name = buffer.getString();
            } else if (opcode == 3) {
                description = buffer.getBytes();
            } else if (opcode == 12) {
                npcSizeInSquares = buffer.getSignedByte();
            } else if (opcode == 13) {
                standAnimation = buffer.getUnsignedShort();
            } else if (opcode == 14) {
                walkAnimation = buffer.getUnsignedShort();
            } else if (opcode == 17) {
                walkAnimation = buffer.getUnsignedShort();
                walkingBackwardsAnimation = buffer.getUnsignedShort();
                walkLeftAnimation = buffer.getUnsignedShort();
                walkRightAnimation = buffer.getUnsignedShort();

                if (walkAnimation == 65535) {
                    walkAnimation = -1;
                }

                if (walkingBackwardsAnimation == 65535) {
                    walkingBackwardsAnimation = -1;
                }

                if (walkLeftAnimation == 65535) {
                    walkLeftAnimation = -1;
                }

                if (walkRightAnimation == 65535) {
                    walkRightAnimation = -1;
                }
            } else if (opcode >= 30 && opcode < 40) {
                if (actions == null) {
                    actions = new String[5];
                }

                actions[opcode - 30] = buffer.getString();

                if (actions[opcode - 30].equalsIgnoreCase("hidden")) {
                    actions[opcode - 30] = null;
                }
            } else if (opcode == 40) {
                int length = buffer.getUnsignedByte();
                changedModelColours = new int[length];
                originalModelColours = new int[length];

                for (int i = 0; i < length; i++) {
                    originalModelColours[i] = buffer.getUnsignedShort();
                    changedModelColours[i] = buffer.getUnsignedShort();
                }
            } else if (opcode == 60) {
                int length = buffer.getUnsignedByte();
                dialogueModels = new int[length];

                for (int i = 0; i < length; i++) {
                    dialogueModels[i] = buffer.getUnsignedShort();
                }
            } else if (opcode == 90) {
                buffer.getUnsignedShort();
            } else if (opcode == 91) {
                buffer.getUnsignedShort();
            } else if (opcode == 92) {
                buffer.getUnsignedShort();
            } else if (opcode == 93) {
                drawYellowDotOnMap = false;
            } else if (opcode == 95) {
                combatLevel = buffer.getUnsignedShort();
            } else if (opcode == 97) {
                adjustVertextPointsXOrY = buffer.getUnsignedShort();
            } else if (opcode == 98) {
                adjustVertextPointZ = buffer.getUnsignedShort();
            } else if (opcode == 99) {
                visibilityOrRendering = true;
            } else if (opcode == 100) {
                modelLightning = buffer.getSignedByte();
            } else if (opcode == 101) {
                modelShadowing = buffer.getSignedByte() * 5;
            } else if (opcode == 102) {
                headIcon = buffer.getUnsignedShort();
            } else if (opcode == 103) {
                degreesToTurn = buffer.getUnsignedShort();
            } else if (opcode == 106) {
                varBitChild = buffer.getUnsignedShort();

                if (varBitChild == 65535) {
                    varBitChild = -1;
                }

                configChild = buffer.getUnsignedShort();

                if (configChild == 65535) {
                    configChild = -1;
                }

                int length = buffer.getUnsignedByte();
                childrenIDs = new int[length + 1];

                for (int i = 0; i <= length; i++) {
                    childrenIDs[i] = buffer.getUnsignedShort();

                    if (childrenIDs[i] == 65535) {
                        childrenIDs[i] = -1;
                    }
                }
            } else if (opcode == 107) {
                disableRightClick = false;
            }
        } while (true);
    }

}