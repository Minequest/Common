What are requirements?
======================

Requirements define the constraints for taking, starting, or switching quests and/or tasks. They allow the quest to limit options based on the environment, including player inventory or characteristics.

We provide the following requirements below in Common. You may utilise them in giving or starting Quests, or with _RequirementEvent_s.

_Player_ typically refers to either the Quest owner or the Group leader.

* **HasItemRequirement**:*item*:*data*:*qty* - Mandate that the player have the specified quantity of item in their inventory.

* **LargeGroupSizeRequirement**:*size* - Mandate that the Quest Group be a specified size or larger.

* **NotRepeatableRequirement** - Mandate that this Quest must not have been completed successfully previously.

* **PlayerRequirement**:*player* - Mandate that this Quest must be owned/led by a specific player.

* **QuestCompletedRequirement**:*quest* - Mandate that the specified Quest be completed by the player first.

* **QuestFailedRequirement**:*quest* - Mandate that the specified Quest be failed by the player first.

* **RealDateRequirement**:*from date*:*to date* - Mandate that this Quest be after the from date and before the to date.

* **SmallGroupSizeRequirement**:*size* - Mandate that the player's Quest Group be a specified size or smaller.

* **WorldRequirement**:*world* - Mandate that the player be in this world.
