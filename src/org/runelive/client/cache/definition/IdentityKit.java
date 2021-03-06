package org.runelive.client.cache.definition;

import org.runelive.client.cache.Archive;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.world.Model;

public final class IdentityKit {

    public static void unpackConfig(Archive streamLoader) {
        ByteBuffer stream = new ByteBuffer(streamLoader.get("idk.dat"));
        length = stream.getUnsignedShort();
        if (cache == null)
            cache = new IdentityKit[length];
        for (int j = 0; j < length; j++) {
            if (cache[j] == null)
                cache[j] = new IdentityKit();
            cache[j].readValues(stream);
            cache[j].anIntArray659[0] = 55232;
            cache[j].anIntArray660[0] = 6798;
        }
        //Fixes weird bandos appearances
        cache[99].anIntArray658[0] = 79000;
        cache[102].anIntArray658[0] = 79001;
        cache[140].anIntArray658[0] = 79002;

        System.out.println("Equipment id: "+cache[0].anIntArray658[0]);
    }

    private void readValues(ByteBuffer stream) {
        do {
            int i = stream.getUnsignedByte();
            if (i == 0)
                return;
            if (i == 1) { //Maybe equipment id
                anInt657 = stream.getUnsignedByte();
            } else if (i == 2) { //Definitely model id
                int j = stream.getUnsignedByte();
                anIntArray658 = new int[j];
                for (int k = 0; k < j; k++) {
                    anIntArray658[k] = stream.getUnsignedShort();
                }
            } else if (i == 3)
                aBoolean662 = true;
            else if (i >= 40 && i < 50) { //Not used
                anIntArray659[i - 40] = stream.getUnsignedShort();
            } else if(i >= 50 && i < 60) { //Not used
                anIntArray660[i - 50] = stream.getUnsignedShort();
            } else if (i >= 60 && i < 70) { //Some sort of model ID
                anIntArray661[i - 60] = stream.getUnsignedShort();
            } else
                System.out.println("Error unrecognised config code: " + i);
        } while (true);
    }

    public boolean isBodyModelLoaded() {
        if (anIntArray658 == null)
            return true;
        boolean flag = true;
        for (int j = 0; j < anIntArray658.length; j++)
            if (!Model.isModelLoaded(anIntArray658[j]))
                flag = false;
        return flag;
    }

    public Model getBodyModel() {
        if (anIntArray658 == null)
            return null;
        Model aclass30_sub2_sub4_sub6s[] = new Model[anIntArray658.length];
        for (int i = 0; i < anIntArray658.length; i++)
            aclass30_sub2_sub4_sub6s[i] = Model.fetchModel(anIntArray658[i]);

        Model model;
        if (aclass30_sub2_sub4_sub6s.length == 1)
            model = aclass30_sub2_sub4_sub6s[0];
        else
            model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
        for (int j = 0; j < 6; j++) {
            if (anIntArray659[j] == 0)
                break;
            model.method476(anIntArray659[j], anIntArray660[j]);
        }

        return model;
    }

    public boolean isDialogModelsLoaded() {
        boolean flag1 = true;
        for (int i = 0; i < 5; i++)
            if (anIntArray661[i] != -1 && !Model.isModelLoaded(anIntArray661[i]))
                flag1 = false;

        return flag1;
    }

    public Model getDialogModel() {
        Model aclass30_sub2_sub4_sub6s[] = new Model[5];
        int j = 0;
        for (int k = 0; k < 5; k++)
            if (anIntArray661[k] != -1)
                aclass30_sub2_sub4_sub6s[j++] = Model.fetchModel(anIntArray661[k]);

        Model model = new Model(j, aclass30_sub2_sub4_sub6s);
        for (int l = 0; l < 6; l++) {
            if (anIntArray659[l] == 0)
                break;
            model.method476(anIntArray659[l], anIntArray660[l]);
        }
        model.scale2(3);
        return model;
    }

    private IdentityKit() {
        anInt657 = -1;
        anIntArray659 = new int[6];
        anIntArray660 = new int[6];
        aBoolean662 = false;
    }

    public static int length;
    public static IdentityKit cache[];
    public int anInt657;
    private int[] anIntArray658;
    private final int[] anIntArray659;
    private final int[] anIntArray660;
    private final int[] anIntArray661 = {
            -1, -1, -1, -1, -1
    };
    public boolean aBoolean662;
}
