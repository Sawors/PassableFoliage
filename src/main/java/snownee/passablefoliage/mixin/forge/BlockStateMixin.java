package snownee.passablefoliage.mixin.forge;

import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.extensions.IForgeBlockState;
import snownee.passablefoliage.PassableFoliage;
import snownee.passablefoliage.PassableFoliageCommonConfig;

@Mixin(BlockStateBase.class)
public class BlockStateMixin implements IForgeBlockState {

	@Override
	public BlockPathTypes getBlockPathType(BlockGetter world, BlockPos pos, @Nullable Mob entity) {
		BlockState self = (BlockState) (Object) this;
		// !PassableFoliageCommonConfig.allEntitiesLeafWalk || (entity instanceof LivingEntity && PassableFoliageCommonConfig.noLeafCheckEntities.contains(entity.getEncodeId()))
		if (!PassableFoliageCommonConfig.allEntitiesLeafWalk && PassableFoliageCommonConfig.modifyPathFinding && PassableFoliage.isPassable(self)) {
			if (entity == null || (!PassableFoliage.hasLeafWalker(entity) && !PassableFoliageCommonConfig.noLeafCheckEntities.contains(entity.getEncodeId()) && !(PassableFoliageCommonConfig.sizeLimitEnabled && entity.getBoundingBox().getSize() <= PassableFoliageCommonConfig.maxBoxSizeForLeafWalk))) {
				return BlockPathTypes.OPEN;
			}
		}
		return self.getBlock().getBlockPathType(self, world, pos, entity);
	}

}
