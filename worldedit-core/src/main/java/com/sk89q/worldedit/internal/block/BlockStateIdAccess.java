/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.internal.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sk89q.worldedit.world.block.BlockState;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.OptionalInt;

import static com.google.common.base.Preconditions.checkState;

public final class BlockStateIdAccess {

    private static final BiMap<BlockState, Integer> ASSIGNED_IDS = HashBiMap.create(2 << 13);

    public static OptionalInt getBlockStateId(BlockState holder) {
        Integer value = ASSIGNED_IDS.get(holder);
        return value == null ? OptionalInt.empty() : OptionalInt.of(value);
    }

    public static @Nullable BlockState getBlockStateById(int id) {
        return ASSIGNED_IDS.inverse().get(id);
    }

    public static void register(BlockState blockState, OptionalInt id) {
        if (id.isPresent()) {
            int i = id.getAsInt();
            BlockState existing = ASSIGNED_IDS.inverse().get(i);
            checkState(existing == null || existing == blockState,
                "BlockState %s is using the same block ID (%s) as BlockState %s",
                blockState, i, existing);
            ASSIGNED_IDS.put(blockState, i);
        }
    }

    public static void clear() {
        ASSIGNED_IDS.clear();
    }

    private BlockStateIdAccess() {
    }

}
