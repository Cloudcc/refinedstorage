package com.raoulvdberge.refinedstorage.api.autocrafting;

import com.raoulvdberge.refinedstorage.api.autocrafting.task.ICraftingTask;
import com.raoulvdberge.refinedstorage.api.util.IComparer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface ICraftingManager {
    /**
     * @return the crafting tasks in this network, do NOT modify this list
     */
    List<ICraftingTask> getTasks();

    /**
     * Adds a crafting task.
     *
     * @param task the task to add
     */
    void add(@Nonnull ICraftingTask task);

    /**
     * Cancels a crafting task.
     *
     * @param task the task to cancel
     */
    void cancel(@Nonnull ICraftingTask task);

    /**
     * Creates a crafting task.
     *
     * @param stack    the stack to create a task for
     * @param pattern  the pattern
     * @param quantity the quantity
     * @return the crafting task
     */
    ICraftingTask create(@Nullable ItemStack stack, ICraftingPattern pattern, int quantity);

    /**
     * Schedules a crafting task if the task isn't scheduled yet.
     *
     * @param stack      the stack
     * @param toSchedule the amount of tasks to schedule
     * @param compare    the compare value to find patterns
     * @return the crafting task created, or null if no task is created
     */
    @Nullable
    ICraftingTask schedule(ItemStack stack, int toSchedule, int compare);

    /**
     * Tracks an incoming stack.
     *
     * @param stack the stack
     */
    void track(ItemStack stack, int size);

    /**
     * @return a list of crafting patterns in this network, do NOT modify this list
     */
    List<ICraftingPattern> getPatterns();

    /**
     * Rebuilds the pattern list.
     */
    void rebuild();

    /**
     * Returns crafting patterns from an item stack.
     *
     * @param pattern the stack to get a pattern for
     * @param flags   the flags to compare on, see {@link IComparer}
     * @return a list of crafting patterns where the given pattern is one of the outputs
     */
    List<ICraftingPattern> getPatterns(ItemStack pattern, int flags);

    /**
     * Returns a crafting pattern for an item stack.
     * This returns a single crafting pattern, as opposed to {@link ICraftingManager#getPatterns(ItemStack, int)}.
     * Internally, this makes a selection out of the available patterns.
     * It makes this selection based on the item count of the pattern outputs in the system.
     *
     * @param pattern the stack to get a pattern for
     * @param flags   the flags to compare on, see {@link IComparer}
     * @return the pattern, or null if the pattern is not found
     */
    @Nullable
    ICraftingPattern getPattern(ItemStack pattern, int flags);

    /**
     * Returns a crafting pattern for an item stack.
     * This returns a single crafting pattern, as opposed to {@link ICraftingManager#getPatterns(ItemStack, int)}.
     * Internally, this makes a selection out of the available patterns.
     * It makes this selection based on the item count of the pattern outputs in the system.
     *
     * @param pattern the stack to get a pattern for
     * @return the pattern, or null if the pattern is not found
     */
    default ICraftingPattern getPattern(ItemStack pattern) {
        return getPattern(pattern, IComparer.COMPARE_DAMAGE | IComparer.COMPARE_NBT);
    }

    /**
     * Returns if there is a pattern with a given stack as output.
     *
     * @param stack the stack
     * @return true if there is a pattern, false otherwise
     */
    default boolean hasPattern(ItemStack stack) {
        return getPattern(stack) != null;
    }

    void update();

    void readFromNBT(NBTTagCompound tag);

    NBTTagCompound writeToNBT(NBTTagCompound tag);
}
