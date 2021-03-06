What are the available events?
==============================

We currently have support for multiple events in our common implementation; and more in the platform-specific versions. You can use them in non-scripted quests.

Below are a listing of the events included with the common implementation, `MineQuest-Common`. Notes below:

- Delays are in *milliseconds* and may not be precise in the actual run.
- *All players* refers to all those in the Quest Group.
- When asking for a task ID, `-1` completes the Quest successfully, `-2` disables task switching.

Events
------

* **CompleteQuestEvent**:*delay* - Completes the Quest with status **SUCCESS** after the delay.

* **FailQuestEvent**:*delay* - Completes the Quest with status **FAIL** after the delay. However, this may vary with Quest implementation.

* **HealthEvent**:*delay*:*percentile* - Sets health of all players to the specified percentile (x out of 100) after the delay.

* **MessageEvent**:*delay*:*message* - Sends all players in the Quest a message after the delay.

* **NameEvent**:*taskmessage* - Adds a sub-task to the Quest description.

* **QuestGiveEvent**:*delay*:*questname* - Give the specified Quest to the owner of the current Quest after the delay. This will automatically be ignored if the owner is not present. (If the owner rejoins later, they will not get the quest.)

* **QuestStartEvent**:*delay*:*questname*:*block*:*task* `beta` - Start the specified Quest in the background after the delay. If blocked, the launcher quest will not proceed until the Quest specified has completed. When this event completes, the next task specified will start. If the owner is not present, this event will fail. This event does not work if used in an instanced quest.

* **RequirementEvent**:*delay*:*requirement ID*:*task if met*:*task if not met* - Check the desired requirement against the group leader after the delay, as specified by its ID. (If not instanced, the quest owner).

* **RewardCmdEvent**:*nexttask*:*command...* - Execute each command for each player, replacing `%p` with the player name; then, go to the next task.

* **RewardItemEvent**:*nexttask*:*material,amount...* - Give each player the specified amount of material (there can be multiple), then move to the next task.

* **TaskEvent**:*delay*:*task* - Triggers the next task specified after the delay.
