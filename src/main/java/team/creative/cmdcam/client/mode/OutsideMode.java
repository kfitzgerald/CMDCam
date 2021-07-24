package team.creative.cmdcam.client.mode;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import team.creative.cmdcam.common.util.CamPath;
import team.creative.cmdcam.common.util.CamPoint;

public class OutsideMode extends CamMode {
    
    public Entity camPlayer;
    
    public OutsideMode(CamPath path) {
        super(path);
        if (path != null)
            this.camPlayer = new ItemEntity(mc.level, 0, 0, 0, ItemStack.EMPTY);
    }
    
    @Override
    public CamMode createMode(CamPath path) {
        return new OutsideMode(path);
    }
    
    @Override
    public String getDescription() {
        return "the player isn't the camera, but you are still in control";
    }
    
    @Override
    public void onPathFinish() {
        super.onPathFinish();
        mc.cameraEntity = mc.player;
    }
    
    @Override
    public void processPoint(CamPoint point) {
        super.processPoint(point);
        
        mc.cameraEntity = camPlayer;
        if (camPlayer instanceof Player)
            ((Player) camPlayer).getAbilities().flying = true;
        
        camPlayer.absMoveTo(point.x, point.y - camPlayer.getEyeHeight(), point.z, (float) point.rotationYaw, (float) point.rotationPitch);
        camPlayer.yRotO = (float) point.rotationYaw;
        camPlayer.xRotO = (float) point.rotationPitch;
        camPlayer.moveTo(point.x, point.y - camPlayer.getEyeHeight(), point.z, (float) point.rotationYaw, (float) point.rotationPitch);
    }
    
}
