/*
 * Hypo, an extensible and pluggable Java bytecode analytical model.
 *
 * Copyright (C) 2021  Kyle Wood (DenWav)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License only.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.denwav.hypo.asm;

import dev.denwav.hypo.model.data.Visibility;
import dev.denwav.hypo.model.data.types.ArrayType;
import dev.denwav.hypo.model.data.types.ClassType;
import dev.denwav.hypo.model.data.types.JvmType;
import dev.denwav.hypo.model.data.types.PrimitiveType;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Utility class for {@code asm} and Hypo compatibility.
 */
public final class HypoAsmUtil {

    /**
     * Returns the {@link Visibility visibility level} from the given access modifiers.
     *
     * @param access The access modifiers to determine visibility from.
     * @return The {@link Visibility visibility level} from the given access modifiers.
     */
    public static @NotNull Visibility accessToVisibility(final int access) {
        if ((access & Opcodes.ACC_PUBLIC) != 0) {
            return Visibility.PUBLIC;
        } else if ((access & Opcodes.ACC_PROTECTED) != 0) {
            return Visibility.PROTECTED;
        } else if ((access & Opcodes.ACC_PRIVATE) != 0) {
            return Visibility.PRIVATE;
        } else {
            return Visibility.PACKAGE;
        }
    }

    /**
     * Map an {@code asm} {@link Type} object into a Hypo {@link JvmType}.
     *
     * @param type The {@code asm} {@link Type} to convert to a Hypo {@link JvmType}.
     * @return A {@link JvmType} which matches the given {@link Type}.
     */
    @SuppressWarnings("ReferenceEquality")
    public static @NotNull JvmType toJvmType(final @NotNull Type type) {
        if (type == Type.CHAR_TYPE) {
            return PrimitiveType.CHAR;
        } else if (type == Type.BYTE_TYPE) {
            return PrimitiveType.BYTE;
        } else if (type == Type.SHORT_TYPE) {
            return PrimitiveType.SHORT;
        } else if (type == Type.INT_TYPE) {
            return PrimitiveType.INT;
        } else if (type == Type.LONG_TYPE) {
            return PrimitiveType.LONG;
        } else if (type == Type.FLOAT_TYPE) {
            return PrimitiveType.FLOAT;
        } else if (type == Type.DOUBLE_TYPE) {
            return PrimitiveType.DOUBLE;
        } else if (type == Type.BOOLEAN_TYPE) {
            return PrimitiveType.BOOLEAN;
        } else if (type == Type.VOID_TYPE) {
            return PrimitiveType.VOID;
        }

        final String desc = type.getDescriptor();
        if (desc.startsWith("[")) {
            return new ArrayType(toJvmType(type.getElementType()), type.getDimensions());
        } else {
            return new ClassType(desc);
        }
    }
}
