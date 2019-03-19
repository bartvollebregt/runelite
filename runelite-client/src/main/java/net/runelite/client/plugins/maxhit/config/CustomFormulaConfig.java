/*
 * Copyright (c) 2019, Bartvollebregt <https://github.com/Bartvollebregt>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.maxhit.config;

import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.plugins.maxhit.calculators.MaxHitCalculator;
import net.runelite.client.plugins.maxhit.equipment.EquipmentItemset;
import net.runelite.client.plugins.maxhit.equipment.EquipmentSlot;
import net.runelite.client.plugins.maxhit.equipment.EquipmentSlotItem;
import net.runelite.client.plugins.maxhit.requirements.EquipmentItemRequirement;
import net.runelite.client.plugins.maxhit.requirements.EquipmentItemSetRequirement;
import net.runelite.client.plugins.maxhit.requirements.Requirement;
import net.runelite.client.plugins.maxhit.requirements.SpellRequirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.BiFunction;

public enum CustomFormulaConfig
{

	MAGIC_DART(
			MaxHitCalculator.CombatMethod.MAGIC,
			new ArrayList<>(Arrays.asList(
					new EquipmentItemRequirement(new EquipmentSlotItem(EquipmentSlot.WEAPON_SLOT, "Slayer's staff")),
					new SpellRequirement(SpellBaseDamageConfig.MAGIC_DART)
			)),
			(client, calculator) ->
			{
				int magicLevel = client.getRealSkillLevel(Skill.MAGIC);
				return Math.floor((magicLevel / 10.0) + 10.0);
			}
	),

	TRIDENT_OF_SEAS(
			MaxHitCalculator.CombatMethod.MAGIC,
			new ArrayList<>(Collections.singletonList(
				new EquipmentItemRequirement(new EquipmentSlotItem(EquipmentSlot.WEAPON_SLOT, "Trident of the seas"))
			)),
			(client, calculator) ->
			{
				int magicLevel = client.getRealSkillLevel(Skill.MAGIC);

				int baseDamage = (int) Math.floor(magicLevel / 3.0) - 5;
				calculator.setBaseDamage(baseDamage);

				return calculator.calculateDefault();
			}
	),

	TRIDENT_OF_SWAMP(
			MaxHitCalculator.CombatMethod.MAGIC,
			new ArrayList<>(Collections.singletonList(
				new EquipmentItemRequirement(new EquipmentSlotItem(EquipmentSlot.WEAPON_SLOT, "Trident of the swamp"))
			)),
			(client, calculator) ->
			{
				int magicLevel = client.getRealSkillLevel(Skill.MAGIC);

				int baseDamage = (int) Math.floor(magicLevel / 3.0) - 2;
				calculator.setBaseDamage(baseDamage);

				return calculator.calculateDefault();
			}
	),

	SWAMP_LIZARD(
			MaxHitCalculator.CombatMethod.MAGIC,
			new ArrayList<>(Collections.singletonList(
				new EquipmentItemRequirement(new EquipmentSlotItem(EquipmentSlot.WEAPON_SLOT, "Swamp lizard"))
			)),
			(client, calculator) ->
			{
				int magicLevel = client.getRealSkillLevel(Skill.MAGIC);
				return Math.floor(0.5 + magicLevel * (64.0 + 56.0) / 640.0);
			}
	),

	ORANGE_SALAMANDER(
			MaxHitCalculator.CombatMethod.MAGIC,
			new ArrayList<>(Collections.singletonList(
				new EquipmentItemRequirement(new EquipmentSlotItem(EquipmentSlot.WEAPON_SLOT, "Orange salamander"))
			)),
			(client, calculator) ->
			{
				int magicLevel = client.getRealSkillLevel(Skill.MAGIC);
				return Math.floor(0.5 + magicLevel * (64.0 + 59.0) / 640.0);
			}
	),

	RED_SALAMANDER(
			MaxHitCalculator.CombatMethod.MAGIC,
			new ArrayList<>(Collections.singletonList(
				new EquipmentItemRequirement(new EquipmentSlotItem(EquipmentSlot.WEAPON_SLOT, "Red salamander"))
			)),
			(client, calculator) ->
			{
				int magicLevel = client.getRealSkillLevel(Skill.MAGIC);
				return Math.floor(0.5 + magicLevel * (64.0 + 77.0) / 640.0);
			}
	),

	BLACK_SALAMANDER(
			MaxHitCalculator.CombatMethod.MAGIC,
			new ArrayList<>(Collections.singletonList(
				new EquipmentItemRequirement(new EquipmentSlotItem(EquipmentSlot.WEAPON_SLOT, "Black salamander"))
			)),
			(client, calculator) ->
			{
				int magicLevel = client.getRealSkillLevel(Skill.MAGIC);
				return Math.floor(0.5 + magicLevel * (64.0 + 92.0) / 640.0);
			}
	),

	DHAROK(
			MaxHitCalculator.CombatMethod.MELEE,
			new ArrayList<>(Collections.singletonList(new EquipmentItemSetRequirement(new EquipmentItemset(Arrays.asList(
					new EquipmentSlotItem(EquipmentSlot.HELM_SLOT, "dharok"),
					new EquipmentSlotItem(EquipmentSlot.CHEST_SLOT, "dharok"),
					new EquipmentSlotItem(EquipmentSlot.LEG_SLOT, "dharok"),
					new EquipmentSlotItem(EquipmentSlot.WEAPON_SLOT, "dharok")
			))))),
			(client, calculator) ->
			{
				int currentHP = client.getBoostedSkillLevel(Skill.HITPOINTS);
				int maxHP = client.getRealSkillLevel(Skill.HITPOINTS);
				int lostHP = maxHP - currentHP;

				double initialMaxHit = calculator.calculate();

				double multiplier = (1.0 + lostHP / 100.0 * maxHP / 100.0);

				return initialMaxHit * multiplier;
			}
	);

	private final MaxHitCalculator.CombatMethod requiredCombatMethod;
	private final ArrayList<Requirement> requirements;
	private final BiFunction<Client, MaxHitCalculator, Double> customFormula;

	CustomFormulaConfig(MaxHitCalculator.CombatMethod requiredCombatMethod, ArrayList<Requirement> requirements, BiFunction<Client, MaxHitCalculator, Double> customFormula)
	{
		this.requiredCombatMethod = requiredCombatMethod;
		this.requirements = requirements;
		this.customFormula = customFormula;
	}

	public MaxHitCalculator.CombatMethod getRequiredCombatMethod()
	{
		return requiredCombatMethod;
	}

	public BiFunction<Client, MaxHitCalculator, Double> getCustomFormula()
	{
		return customFormula;
	}

	public ArrayList<Requirement> getRequirements()
	{
		return this.requirements;
	}
}
