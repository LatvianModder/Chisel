package team.chisel.client.render;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.BlockPartRotation;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import org.lwjgl.util.vector.Vector3f;
import team.chisel.api.render.TextureSpriteCallback;
import team.chisel.client.render.ctm.CTMFaceBakery;

import java.util.List;

/**
 * Helper class for quad stuff
 */
public class QuadHelper {

    private static final CTMFaceBakery ctmBakery = new CTMFaceBakery();

    private static final FaceBakery bakery = new FaceBakery();

    private static final QuadPos quadPos = new QuadPos(new Vector3f(0, 0, 0), new Vector3f(16, 16, 16));


    /**
     * Make a normal quad for a face
     * @param f The Face
     * @param sprite The Sprite
     * @return The Quad
     */
    public static BakedQuad makeNormalFaceQuad(EnumFacing f, TextureAtlasSprite sprite){
        return makeUVFaceQuad(f, sprite, new float[]{0, 0, 16, 16});
    }

    public static BakedQuad makeFourQuads(EnumFacing f, TextureAtlasSprite sprite, float[] uvs){
        for (int i = 0 ; i < 4 ; i++ ){
            QuadPos pos = CTMFaceBakery.getCorrectQuadPos(f, i);

            float uDif = uvs[2] - uvs[0];
            float vDif = uvs[1] - uvs[3];
            float[] uvsOut = {uvs[0], uvs[1], uvs[0]+uDif, uvs[1] + vDif);
            if (i == 3){
                uvsOut = {uvs[2]/2, }
            }
            //todo Finish this
        }
    }

    /**
     * Make a face quad with the specified uvs
     * @param f The Face
     * @param sprite The Sprite
     * @param uvs The uvs
     * @return The Quad
     */
    public static BakedQuad makeUVFaceQuad(EnumFacing f, TextureAtlasSprite sprite, float[] uvs){
        makeUVQuad(f, sprite, uvs, quadPos);
    }

    public static BakedQuad makeUVQuad(EnumFacing f, TextureAtlasSprite sprite, float[] uvs, QuadPos pos){
        return bakery.makeBakedQuad(pos.from, pos.to, new BlockPartFace(f, -1, sprite.getIconName(), new BlockFaceUV(uvs, 0)),
                sprite, f, ModelRotation.X0_Y0, new BlockPartRotation(new Vector3f(1, 0, 0), f.getAxis(), 0, false), false, false);
    }

    /**
     * Makes a ctm face
     *
     * @param side      The Side
     * @param sprites The Sprites
     * @param quads     The Quad "ids"
     * @return The CTM Face
     */
    public static List<BakedQuad> makeCtmFace(EnumFacing side, TextureAtlasSprite[] sprites, int[] quads) {
        return ctmBakery.makeCtmFace(side, sprites, quads);
    }

    /**
     * Makes a ctm face
     *
     * @param side      The Side
     * @param sprites The Sprites
     * @param quads     The Quad "ids"
     * @return The CTM Face
     */
    public static List<BakedQuad> makeCtmFace(EnumFacing side, TextureSpriteCallback[] sprites, int[] quads) {
        TextureAtlasSprite[] realSprites = new TextureAtlasSprite[sprites.length];
        for (int i = 0 ; i < sprites.length ; i++){
            realSprites[i] = sprites[i].getSprite();
        }
        return ctmBakery.makeCtmFace(side, realSprites, quads);
    }
}
