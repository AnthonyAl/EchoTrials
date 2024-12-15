package com.unipi.alexandris.game.echotrials.base.handlers;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.GameLevel;
import com.unipi.alexandris.game.echotrials.base.core.ID;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;
import com.unipi.alexandris.game.echotrials.base.loaders.SoundFXLoader;
import com.unipi.alexandris.game.echotrials.base.roomobjects.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Manages and coordinates trigger-based events and interactions in the game.
 * This handler is responsible for:
 * - Managing trigger zones and their associated actions
 * - Scheduling and executing timed trigger events
 * - Coordinating level-specific trigger mechanics
 * - Handling button and trap block interactions
 * - Managing moving platform synchronization
 * - Controlling spike trap activation sequences
 * 
 * The handler maintains a mapping between trigger blocks and their actions,
 * supports delayed and repeated trigger activations, and provides mechanisms
 * for both immediate and scheduled event execution.
 */
public class TriggerHandler {
    /** Game object handler for managing entities */
    private final Handler handler;

    /**
     * Record for storing button size configuration.
     * Used to define button dimensions for different button types.
     */
    public record ButtonSizeSetting(ButtonBlock.ButtonType type, double width, double height) {}

    /**
     * Record for storing button movement parameters.
     * Defines physics properties for different button types.
     */
    public record ButtonMovementSetting(ButtonBlock.ButtonType type, double speed, double jump, double gravity) {}

    /** Maps trigger blocks to their associated actions */
    private final HashMap<TriggerBlock, Runnable> triggerActions = new HashMap<>();

    public TriggerHandler(Handler handler) {
        this.handler = handler;
    }

    public void buildLevel(List<GameObject> portalBlocks, GameLevel gameLevel) {
        switch(gameLevel.levelID()) {
            case LEVEL_SELECTOR -> {
                Game.player.setCancelUP(true);

                String[] titles = {"EXIT GAME", "SPEEDRUN", "PITS", "SPIKES", "PUSH", "ICE", "MOVEMENT", "COMING SOON", "COMING SOON", "COMING SOON", "COMING SOON", "COMING SOON"};
                LevelID[] destinations = {LevelID.LEVEL_SELECTOR, LevelID.LEVEL_A_I, LevelID.LEVEL_A_I, LevelID.LEVEL_B_I, LevelID.LEVEL_C_I, LevelID.LEVEL_D_I, LevelID.LEVEL_E_I,
                        LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR};

                int c = 0;
                for (int[] j : gameLevel.triggerBlockCoords()) {
                    TriggerBlock triggerBlock = new TriggerBlock(j[0], j[1], ID.TriggerBlock, 0, 1, this, 1, 1, 16, 16);

                    int finalC = c;
                    Runnable runnable = () -> {
                        PortalBlock portalBlock = ((PortalBlock)portalBlocks.get(finalC));
                        portalBlock.setDestination(titles[finalC], destinations[finalC]);

                        if(!Game.unlockedLevels.contains(portalBlock.getDestination().group)) portalBlock.lock();
                        if(Objects.equals(portalBlock.getTitle(), "EXIT GAME")) portalBlock.unlock();
                        if(Objects.equals(portalBlock.getTitle(), "SPEEDRUN")) {
                            if(Game.unlockedLevels.contains("SPEEDRUN")) portalBlock.unlock();
                            else portalBlock.lock();
                        }
                    };

                    triggerActions.put(triggerBlock, runnable);
                    handler.addObject(triggerBlock);
                    c++;
                }
            }
            case LEVEL_HIGHSCORE -> {
                Game.player.setCancelUP(true);

                String[] titles = {"EXIT GAME", "SPEEDRUN", "PITS", "SPIKES", "PUSH", "ICE", "MOVEMENT", "COMING SOON", "COMING SOON", "COMING SOON", "COMING SOON", "COMING SOON"};
                String[] groups = {"MAIN", "MAIN", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
                LevelID[] destinations = {LevelID.LEVEL_SELECTOR, LevelID.LEVEL_A_I,
                        LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR,
                        LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR, LevelID.LEVEL_SELECTOR};

                int c = 0;
                HashMap<String, String> highScores = Game.getHighScores();
                for (int[] j : gameLevel.triggerBlockCoords()) {
                    TriggerBlock triggerBlock = new TriggerBlock(j[0], j[1], ID.TriggerBlock, 0, 1, this, 1, 1, 16, 16);

                    int finalC = c;
                    Runnable runnable = () -> {
                        PortalBlock portalBlock = ((PortalBlock)portalBlocks.get(finalC));

                        portalBlock.lock();
                        if(Objects.equals(titles[finalC], "EXIT GAME")) {
                            portalBlock.unlock();
                            portalBlock.setDestination(titles[finalC], destinations[finalC]);
                        }
                        else if(Objects.equals(titles[finalC], "SPEEDRUN")) {
                            if (Game.unlockedLevels.contains("SPEEDRUN")) portalBlock.unlock();
                            else portalBlock.lock();
                            portalBlock.setDestination(titles[finalC], destinations[finalC]);
                        }
                        else {
                            String group = groups[finalC];
                            String score = highScores.get("LEVEL_"+group);
                            String title = titles[finalC] + "\n" + "BEST TIME\n" + score;
                            portalBlock.setDestination(title, destinations[finalC]);
                        }

                    };

                    triggerActions.put(triggerBlock, runnable);
                    handler.addObject(triggerBlock);
                    c++;
                }
            }
            case LEVEL_TEST_I -> {

                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register Trigger Blocks
                ArrayList<List<GameObject>> targetedTraps = new ArrayList<>();
                List<GameObject> trap1 = new ArrayList<>();
                List<GameObject> trap2 = new ArrayList<>();
                List<GameObject> trap3 = new ArrayList<>();
                for(int t = 0; t <= 81; t+=9) {
                    trap1.addAll(new ArrayList<>(trapBlocks).subList(t,t+2));
                    trap2.addAll(new ArrayList<>(trapBlocks).subList(t+2,t+5));
                    trap3.addAll(new ArrayList<>(trapBlocks).subList(t+5,t+9));
                }
                targetedTraps.add(trap1);
                targetedTraps.add(trap2);
                targetedTraps.add(trap3);

                int c = 0;
                for (int[] j : gameLevel.triggerBlockCoords()) {
                    TriggerBlock triggerBlock = new TriggerBlock(j[0], j[1], ID.TriggerBlock, 0, 1, this, 1);

                    Runnable runnable = getTrapBlockRunnable(targetedTraps.get(c), c+2);

                    triggerActions.put(triggerBlock, runnable);
                    handler.addObject(triggerBlock);
                    c++;
                }

                // Register Button Blocks
                handler.addObject(new ButtonBlock(gameLevel.buttonBlockCoords().get(0)[0], gameLevel.buttonBlockCoords().get(0)[1],
                        ID.ButtonBlock, this,  new ButtonMovementSetting(ButtonBlock.ButtonType.SPEED, 5, 0, 1)));
                handler.addObject(new ButtonBlock(gameLevel.buttonBlockCoords().get(1)[0], gameLevel.buttonBlockCoords().get(1)[1],
                        ID.ButtonBlock, this,  new ButtonMovementSetting(ButtonBlock.ButtonType.SPEED, -5, -20, -1)));
                handler.addObject(new ButtonBlock(gameLevel.buttonBlockCoords().get(2)[0], gameLevel.buttonBlockCoords().get(2)[1],
                        ID.ButtonBlock, this, new ButtonSizeSetting(ButtonBlock.ButtonType.SIZE, 5, 5)));
                handler.addObject(new ButtonBlock(gameLevel.buttonBlockCoords().get(3)[0], gameLevel.buttonBlockCoords().get(3)[1],
                        ID.ButtonBlock, this, new ButtonMovementSetting(ButtonBlock.ButtonType.SPEED, 15, 20, 1)));
                handler.addObject(new ButtonBlock(gameLevel.buttonBlockCoords().get(4)[0], gameLevel.buttonBlockCoords().get(4)[1],
                        ID.ButtonBlock, this, new ButtonMovementSetting(ButtonBlock.ButtonType.SPEED, 5, 800, 1)));
                handler.addObject(new ButtonBlock(gameLevel.buttonBlockCoords().get(5)[0], gameLevel.buttonBlockCoords().get(5)[1],
                        ID.ButtonBlock, this, new ButtonMovementSetting(ButtonBlock.ButtonType.SPEED, 5, 20, 0.3)));

            }
            case LEVEL_TEST_II -> {

                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register moving Blocks
                int c = 0;
                ArrayList<GameObject> movingBlocks = new ArrayList<>();
                for (int[] j : gameLevel.movingBlockCoords()) {
                    MovingBlock tempBlock = new MovingBlock(j[0], j[1], ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                    if(c==1) {
                        tempBlock.setMovement(() -> {
                            Game.player.removeObstructions(tempBlock.getArea());
                            tempBlock.moveY(-48);
                            Game.player.addExtraObstructions(tempBlock.getArea());
                        });
                    }
                    else if(c==3) {
                        tempBlock.setMovement(() -> {
                            Game.player.removeObstructions(tempBlock.getArea());
                            tempBlock.moveY(-48);
                            Game.player.addExtraObstructions(tempBlock.getArea());
                        });
                    }
                    else {
                        tempBlock.setMovement(() -> {
                            Game.player.removeObstructions(tempBlock.getArea());
                            tempBlock.moveY(24);
                            Game.player.addExtraObstructions(tempBlock.getArea());
                        });
                    }
                    handler.addObject(tempBlock);
                    movingBlocks.add(tempBlock);
                    Game.player.addExtraObstructions(tempBlock.getArea());
                    c++;
                }

                List<GameObject> trap1 = new ArrayList<>();
                List<GameObject> trap2 = new ArrayList<>();
                List<GameObject> trap3 = new ArrayList<>();
                List<GameObject> trap4 = new ArrayList<>();
                List<GameObject> trap5 = new ArrayList<>();
                for(int t = 0; t <= 99; t+=11) {
                    trap1.addAll(new ArrayList<>(trapBlocks).subList(t,t+4));
                    trap3.addAll(new ArrayList<>(trapBlocks).subList(t+4,t+7));
                    trap4.addAll(new ArrayList<>(trapBlocks).subList(t+7,t+11));
                }
                for(int t = 0; t < 12; t++) {
                    if(t==1 || t==3) {
                        trap5.add(new ArrayList<>(movingBlocks).get(t));
                        continue;
                    }
                    trap2.add(new ArrayList<>(movingBlocks).get(t));
                }


                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, 4, this, 1);
                runnable = () -> {
                    for(GameObject gameObject : trap5) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, this, 1);

                runnable = getTrapBlockRunnable(trap3, 3);

                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(2)[0], gameLevel.triggerBlockCoords().get(2)[1],
                        ID.TriggerBlock, 0, 1, this, 0,1,3,10);

                runnable = getTrapBlockRunnable(trap4, 4);

                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(3)[0], gameLevel.triggerBlockCoords().get(3)[1],
                        ID.TriggerBlock, 0, 1, this, 1);

                runnable = getTrapBlockRunnable(trap1, 4);

                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(4)[0], gameLevel.triggerBlockCoords().get(4)[1],
                        ID.TriggerBlock, 0, 1, 60, this, 0,1,3,10);
                runnable = () -> {
                    for(GameObject gameObject : trap2) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_A_I -> {
                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register Trigger Blocks

                TriggerBlock triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, this, 0, 4, 6, 6);

                Runnable runnable = getTrapBlockRunnable(trapBlocks, 4);

                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_A_II -> {
                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register Trigger Blocks
                List<GameObject> trap1 = new ArrayList<>();
                List<GameObject> trap2 = new ArrayList<>();
                for(int t = 0; t <= 30; t+=6) {
                    trap1.addAll(new ArrayList<>(trapBlocks).subList(t,t+4));
                    trap2.addAll(new ArrayList<>(trapBlocks).subList(t+4,t+6));
                }

                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, this, 0, 4, 6, 6);

                runnable = getTrapBlockRunnable(trap1, 4);

                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, this, 0, 2, 6, 6);

                runnable = getTrapBlockRunnable(trap2, 2);

                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_A_III -> {
                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register moving Blocks
                int c = 0;
                ArrayList<GameObject> movingBlocks = new ArrayList<>();
                for (int[] j : gameLevel.movingBlockCoords()) {
                    MovingBlock tempBlock = new MovingBlock(j[0], j[1], ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                    if(c==1) {
                        tempBlock.setMovement(() -> {
                            Game.player.removeObstructions(tempBlock.getArea());
                            tempBlock.moveY(-46);
                            Game.player.addExtraObstructions(tempBlock.getArea());
                        });
                    }
                    else if(c==3) {
                        tempBlock.setMovement(() -> {
                            Game.player.removeObstructions(tempBlock.getArea());
                            tempBlock.moveY(-46);
                            Game.player.addExtraObstructions(tempBlock.getArea());
                        });
                    }
                    else if(c==5) {
                        tempBlock.setMovement(() -> {
                            Game.player.removeObstructions(tempBlock.getArea());
                            tempBlock.moveY(-46);
                            Game.player.addExtraObstructions(tempBlock.getArea());
                        });
                    }
                    else {
                        tempBlock.setMovement(() -> {
                            Game.player.removeObstructions(tempBlock.getArea());
                            tempBlock.moveY(24);
                            Game.player.addExtraObstructions(tempBlock.getArea());
                        });
                    }
                    handler.addObject(tempBlock);
                    movingBlocks.add(tempBlock);
                    Game.player.addExtraObstructions(tempBlock.getArea());
                    c++;
                }

                List<GameObject> trap1 = new ArrayList<>();
                List<GameObject> trap2 = new ArrayList<>();
                List<GameObject> trap3 = new ArrayList<>();
                List<GameObject> trap4 = new ArrayList<>();
                List<GameObject> trap5 = new ArrayList<>();
                for(int t = 0; t <= 48; t+=9) {
                    trap1.addAll(new ArrayList<>(trapBlocks).subList(t,t+4));
                    trap2.addAll(new ArrayList<>(trapBlocks).subList(t+4,t+7));
                    trap5.addAll(new ArrayList<>(trapBlocks).subList(t+7,t+9));
                }
                for(int t = 0; t < 9; t++) {
                    if(t==1 || t==3 || t==5) {
                        trap4.add(new ArrayList<>(movingBlocks).get(t));
                        continue;
                    }
                    trap3.add(new ArrayList<>(movingBlocks).get(t));
                }

                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, this, 0, 3, 6, 6);
                runnable = getTrapBlockRunnable(trap1, 4);
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, this, 0, 3, 6, 6);
                runnable = getTrapBlockRunnable(trap2, 3);
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(2)[0], gameLevel.triggerBlockCoords().get(2)[1],
                        ID.TriggerBlock, 0, 1, 30, this, 0,1,6,6);
                runnable = () -> {
                    for(GameObject gameObject : trap3) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(3)[0], gameLevel.triggerBlockCoords().get(3)[1],
                        ID.TriggerBlock, 0, 1, 2, this, 0, 1, 6, 6);
                runnable = () -> {
                    for(GameObject gameObject : trap4) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(4)[0], gameLevel.triggerBlockCoords().get(4)[1],
                        ID.TriggerBlock, 0, 1, this, 0,1,6,6);
                runnable = getTrapBlockRunnable(trap5, 1);
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_A_IV -> {
                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register moving Blocks
                int c = 0;
                ArrayList<GameObject> movingBlocks = new ArrayList<>();

                MovingBlock tempBlock = new MovingBlock(gameLevel.movingBlockCoords().get(26)[0], gameLevel.movingBlockCoords().get(26)[1],
                        ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                MovingBlock finalTempBlock = tempBlock;
                tempBlock.setMovement(() -> {
                    Game.player.removeObstructions(finalTempBlock.getArea());
                    finalTempBlock.moveY(-48);
                    Game.player.addExtraObstructions(finalTempBlock.getArea());
                });
                handler.addObject(tempBlock);
                movingBlocks.add(tempBlock);
                Game.player.addExtraObstructions(tempBlock.getArea());

                tempBlock = new MovingBlock(gameLevel.movingBlockCoords().get(53)[0], gameLevel.movingBlockCoords().get(53)[1],
                        ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                MovingBlock finalTempBlock1 = tempBlock;
                tempBlock.setMovement(() -> {
                    Game.player.removeObstructions(finalTempBlock1.getArea());
                    finalTempBlock1.moveY(-48);
                    Game.player.addExtraObstructions(finalTempBlock1.getArea());
                });
                handler.addObject(tempBlock);
                movingBlocks.add(tempBlock);
                Game.player.addExtraObstructions(tempBlock.getArea());

                tempBlock = new MovingBlock(gameLevel.movingBlockCoords().get(80)[0], gameLevel.movingBlockCoords().get(80)[1],
                        ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                MovingBlock finalTempBlock2 = tempBlock;
                tempBlock.setMovement(() -> {
                    Game.player.removeObstructions(finalTempBlock2.getArea());
                    finalTempBlock2.moveY(-48);
                    Game.player.addExtraObstructions(finalTempBlock2.getArea());
                });
                handler.addObject(tempBlock);
                movingBlocks.add(tempBlock);
                Game.player.addExtraObstructions(tempBlock.getArea());

                for (int[] j : gameLevel.movingBlockCoords()) {
                    MovingBlock finalTempBlock3 = new MovingBlock(j[0], j[1], ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                    if(c==26) {
                        c++;
                        continue;
                    }
                    else if(c==53) {
                        c++;
                        continue;
                    }
                    else if(c==80) {
                        c++;
                        continue;
                    }
                    else {
                        finalTempBlock3.setMovement(() -> {
                            Game.player.removeObstructions(finalTempBlock3.getArea());
                            finalTempBlock3.moveX(17);
                            Game.player.addExtraObstructions(finalTempBlock3.getArea());
                        });
                    }
                    handler.addObject(finalTempBlock3);
                    movingBlocks.add(finalTempBlock3);
                    Game.player.addExtraObstructions(finalTempBlock3.getArea());
                    c++;
                }

                List<GameObject> trap1 = new ArrayList<>(new ArrayList<>(trapBlocks));
                List<GameObject> trap2 = new ArrayList<>();
                List<GameObject> trap3 = new ArrayList<>();

                for(int t = 0; t < movingBlocks.size(); t++) {
                    if(t==0 || t==1 || t==2) {
                        trap3.add(new ArrayList<>(movingBlocks).get(t));
                        continue;
                    }
                    trap2.add(new ArrayList<>(movingBlocks).get(t));
                }

                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, this, 0, 3, 6, 6);
                runnable = getTrapBlockRunnable(trap1, 4);
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, 170, this, 0, 0, 6, 6);
                runnable = () -> {
                    for(GameObject gameObject : trap2) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(2)[0], gameLevel.triggerBlockCoords().get(2)[1],
                        ID.TriggerBlock, 0, 1, 2, this, 0,1,6,6);
                runnable = () -> {
                    for(GameObject gameObject : trap3) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_A_V -> {
                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register moving Blocks
                int c = 0;
                ArrayList<GameObject> movingBlocks = new ArrayList<>();

                MovingBlock finalTempBlock = new MovingBlock(gameLevel.movingBlockCoords().get(31)[0], gameLevel.movingBlockCoords().get(31)[1],
                        ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                finalTempBlock.setMovement(() -> {
                    Game.player.removeObstructions(finalTempBlock.getArea());
                    finalTempBlock.moveY(-48);
                    Game.player.addExtraObstructions(finalTempBlock.getArea());
                });
                handler.addObject(finalTempBlock);
                movingBlocks.add(finalTempBlock);
                Game.player.addExtraObstructions(finalTempBlock.getArea());

                MovingBlock finalTempBlock1 = new MovingBlock(gameLevel.movingBlockCoords().get(63)[0], gameLevel.movingBlockCoords().get(63)[1],
                        ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                finalTempBlock1.setMovement(() -> {
                    Game.player.removeObstructions(finalTempBlock1.getArea());
                    finalTempBlock1.moveY(-48);
                    Game.player.addExtraObstructions(finalTempBlock1.getArea());
                });
                handler.addObject(finalTempBlock1);
                movingBlocks.add(finalTempBlock1);
                Game.player.addExtraObstructions(finalTempBlock1.getArea());

                MovingBlock finalTempBlock2 = new MovingBlock(gameLevel.movingBlockCoords().get(95)[0], gameLevel.movingBlockCoords().get(95)[1],
                        ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                finalTempBlock2.setMovement(() -> {
                    Game.player.removeObstructions(finalTempBlock2.getArea());
                    finalTempBlock2.moveY(-48);
                    Game.player.addExtraObstructions(finalTempBlock2.getArea());
                });
                handler.addObject(finalTempBlock2);
                movingBlocks.add(finalTempBlock2);
                Game.player.addExtraObstructions(finalTempBlock2.getArea());

                for (int[] j : gameLevel.movingBlockCoords()) {
                    MovingBlock finalTempBlock3 = new MovingBlock(j[0], j[1], ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                    if(c==31) {
                        c++;
                        continue;
                    }
                    else if(c==63) {
                        c++;
                        continue;
                    }
                    else if(c==95) {
                        c++;
                        continue;
                    }
                    else {
                        finalTempBlock3.setMovement(() -> {
                            Game.player.removeObstructions(finalTempBlock3.getArea());
                            finalTempBlock3.moveY(10);
                            Game.player.addExtraObstructions(finalTempBlock3.getArea());
                        });
                    }
                    handler.addObject(finalTempBlock3);
                    movingBlocks.add(finalTempBlock3);
                    Game.player.addExtraObstructions(finalTempBlock3.getArea());
                    c++;
                }

                List<GameObject> trap1 = new ArrayList<>();
                List<GameObject> trap2 = new ArrayList<>(new ArrayList<>(trapBlocks));
                List<GameObject> trap3 = new ArrayList<>();

                for(int t = 0; t < movingBlocks.size(); t++) {
                    if(t==0 || t==1 || t==2) {
                        trap3.add(new ArrayList<>(movingBlocks).get(t));
                        continue;
                    }
                    trap1.add(new ArrayList<>(movingBlocks).get(t));
                }

                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 150, 7, 340, this, 0, 0, 6, 6);
                runnable = () -> {
                    for(GameObject gameObject : trap1) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, this, 0, 1, 6, 6);
                runnable = getTrapBlockRunnable(trap2, 2);
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(2)[0], gameLevel.triggerBlockCoords().get(2)[1],
                        ID.TriggerBlock, 0, 1, 2, this, 0,1,6,6);
                runnable = () -> {
                    for(GameObject gameObject : trap3) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_B_I -> {

                // Register Spike Blocks
                ArrayList<SpikeBlock> spikes = new ArrayList<>();
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                    spikes.add(tempBlock);
                }

                // Register Trigger Blocks

                TriggerBlock triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, 6, this, 1, 0, 2, 1);

                Runnable runnable = () -> {
                    int c = 0;
                    for(SpikeBlock spike : spikes) {
                        if(c++ == 5) spike.moveX(-12);
                    }
                };

                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_B_II -> {

                // Register Spike Blocks
                ArrayList<SpikeBlock> spikes = new ArrayList<>();
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                    spikes.add(tempBlock);
                }

                // Register Trigger Blocks
                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, 5, this, -2, 3, 5, 3);
                runnable = () -> {
                    int c = 0;
                    for(SpikeBlock spike : spikes) {
                        if(c == 3 || c == 4) spike.moveX(-24);
                        c++;
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, 6, this, 1, 0, 2, 1);
                runnable = () -> {
                    int c = 0;
                    for(SpikeBlock spike : spikes) {
                        if(c++ == 5) spike.moveX(-12);
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_B_III -> {
                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register Spike Blocks
                ArrayList<SpikeBlock> spikes = new ArrayList<>();
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                    spikes.add(tempBlock);
                }

                // Register Trigger Blocks
                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, this, -1, 2, 5, 3);
                runnable = getTrapBlockRunnable(trapBlocks, 4);
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, 5, this, 1, 0, 2, 1);
                runnable = () -> {
                    int c = 0;
                    for(SpikeBlock spike : spikes) {
                        if(c == 7 || c == 8) spike.moveX(+24);
                        if(c == 5) spike.moveX(-24);
                        if(c == 6 || c == 7) spike.moveY(+12);
                        c++;
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

            }
            case LEVEL_B_IV -> {
                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register Spike Blocks
                ArrayList<SpikeBlock> spikes = new ArrayList<>();
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                    spikes.add(tempBlock);
                }

                // Register Trigger Blocks
                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, 5, this, 0, 3, 5, 3);
                runnable = () -> {
                    for(SpikeBlock spike : spikes) {
                        spike.moveX(+24);
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, this, 0, 2, 5, 3);

                runnable = getTrapBlockRunnable(trapBlocks, 2);

                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(3)[0], gameLevel.triggerBlockCoords().get(3)[1],
                        ID.TriggerBlock, 0, 1, 7, this, 2, 0, 3, 2);
                runnable = () -> {
                    for(SpikeBlock spike : spikes) {
                        spike.moveX(+24);
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(2)[0], gameLevel.triggerBlockCoords().get(2)[1],
                        ID.TriggerBlock, 0, 1, 46, this, -1, 2, 3, 2);
                runnable = () -> {
                    for(SpikeBlock spike : spikes) {
                        spike.moveX(-24);
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

            }
            case LEVEL_B_V -> {


                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register Spike Blocks
                ArrayList<SpikeBlock> spikes = new ArrayList<>();
                int s = 0;
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    if(s++ > 32)tempBlock.arise();
                    spikes.add(tempBlock);
                }

                // Register Trigger Blocks
                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 6, 170, this, 1, 0, 5, 3);
                runnable = new Runnable() {
                    int c = 0;
                    @Override
                    public void run() {
                        for (SpikeBlock spike : spikes)
                            if (c < 31 && spike.isHidden()) {
                                spike.arise();
                                break;
                            }
                        c++;
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, this, 0, 3, 5, 3);
                runnable = getTrapBlockRunnable(trapBlocks, 4);
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(2)[0], gameLevel.triggerBlockCoords().get(2)[1],
                        ID.TriggerBlock, 0, 1, 5, this, 1, 0, 2, 1);
                runnable = () -> {
                    int c = 0;
                    for(SpikeBlock spike : spikes) {
                        if(c > 30) spike.arise();
                        c++;
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_C_I -> {
                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register moving Blocks
                ArrayList<GameObject> movingBlocks = new ArrayList<>();

                MovingBlock finalTempBlock = new MovingBlock(gameLevel.movingBlockCoords().getFirst()[0], gameLevel.movingBlockCoords().getFirst()[1],
                        ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                finalTempBlock.setMovement(new Runnable() {
                    final double currentPos = finalTempBlock.getY();
                    @Override
                    public void run() {
                        if(finalTempBlock.getY() > currentPos-48) {
                            Game.player.removeObstructions(finalTempBlock.getArea());
                            finalTempBlock.moveY(-24);
                            Game.player.addExtraObstructions(finalTempBlock.getArea());
                        }
                        else {
                            Game.player.removeObstructions(finalTempBlock.getArea());
                            finalTempBlock.moveX(-24);
                            Game.player.addExtraObstructions(finalTempBlock.getArea());
                        }
                    }
                });
                handler.addObject(finalTempBlock);
                movingBlocks.add(finalTempBlock);
                Game.player.addExtraObstructions(finalTempBlock.getArea());

                List<GameObject> trap1 = new ArrayList<>(new ArrayList<>(trapBlocks));
                List<GameObject> trap2 = new ArrayList<>(new ArrayList<>(movingBlocks));

                // Register trigger blocks
                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, this, 0, 1, 6, 6);
                runnable = getTrapBlockRunnable(trap1, 4);
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, 8, this, 0, 0, 6, 6);
                runnable = () -> {
                    for(GameObject gameObject : trap2) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_C_II -> {
                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register moving Blocks
                ArrayList<GameObject> movingBlocks = new ArrayList<>();

                MovingBlock finalTempBlock = new MovingBlock(gameLevel.movingBlockCoords().getFirst()[0], gameLevel.movingBlockCoords().getFirst()[1],
                        ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                finalTempBlock.setMovement(new Runnable() {
                    final double currentPos = finalTempBlock.getY();
                    @Override
                    public void run() {
                        if(finalTempBlock.getY() > currentPos-48) {
                            Game.player.removeObstructions(finalTempBlock.getArea());
                            finalTempBlock.moveY(-24);
                            Game.player.addExtraObstructions(finalTempBlock.getArea());
                        }
                        else {
                            Game.player.removeObstructions(finalTempBlock.getArea());
                            finalTempBlock.moveX(+24);
                            Game.player.addExtraObstructions(finalTempBlock.getArea());
                        }
                    }
                });
                handler.addObject(finalTempBlock);
                movingBlocks.add(finalTempBlock);
                Game.player.addExtraObstructions(finalTempBlock.getArea());

                List<GameObject> trap1 = new ArrayList<>(new ArrayList<>(trapBlocks));
                List<GameObject> trap2 = new ArrayList<>(new ArrayList<>(movingBlocks));

                // Register trigger blocks
                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, this, 0, 1, 6, 6);
                runnable = getTrapBlockRunnable(trap1, 4);
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, 10, this, 0, 0, 6, 6);
                runnable = () -> {
                    for(GameObject gameObject : trap2) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_C_III -> {
                // Register Spike Blocks
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                }

                // Register moving Blocks
                ArrayList<GameObject> movingBlocks = new ArrayList<>();

                MovingBlock finalTempBlock = new MovingBlock(gameLevel.movingBlockCoords().getFirst()[0], gameLevel.movingBlockCoords().getFirst()[1],
                        ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                finalTempBlock.setMovement(new Runnable() {
                    final double currentPos = finalTempBlock.getY();
                    @Override
                    public void run() {
                        if(finalTempBlock.getY() > currentPos-48) {
                            Game.player.removeObstructions(finalTempBlock.getArea());
                            finalTempBlock.moveY(-24);
                            Game.player.addExtraObstructions(finalTempBlock.getArea());
                        }
                        else {
                            Game.player.removeObstructions(finalTempBlock.getArea());
                            finalTempBlock.moveX(+24);
                            Game.player.addExtraObstructions(finalTempBlock.getArea());
                        }
                    }
                });
                handler.addObject(finalTempBlock);
                movingBlocks.add(finalTempBlock);
                Game.player.addExtraObstructions(finalTempBlock.getArea());

                List<GameObject> trap1 = new ArrayList<>(new ArrayList<>(movingBlocks));

                // Register trigger blocks
                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, 20, this, 0, 0, 5, 3);
                runnable = () -> {
                    for(GameObject gameObject : trap1) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, 32, this, 0, 0, 2, 12);
                runnable = new Runnable() {
                    int iterations = 0;
                    @Override
                    public void run() {
                        for (GameObject gameObject : trap1) {
                            if (gameObject instanceof MovingBlock movingBlock) {
                                if(iterations > 27) {
                                    Game.player.removeObstructions(movingBlock.getArea());
                                    movingBlock.moveY(-24);
                                    Game.player.addExtraObstructions(movingBlock.getArea());
                                }
                                if(iterations >= 4 && iterations < 6) {
                                    Game.player.removeObstructions(movingBlock.getArea());
                                    movingBlock.moveX(24);
                                    Game.player.addExtraObstructions(movingBlock.getArea());
                                }
                                if(iterations < 6) {
                                    Game.player.removeObstructions(movingBlock.getArea());
                                    movingBlock.moveY(24);
                                    Game.player.addExtraObstructions(movingBlock.getArea());
                                }
                                iterations++;
                            }
                        }
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_C_IV -> {
                // Register trap blocks.
                ArrayList<GameObject> trapBlocks = new ArrayList<>();
                for(int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock tempBlock = new TrapBlock(j[0], j[1], ID.TrapBlock, Game.gameImages.trapBlockImages()[0]);
                    handler.addObject(tempBlock);
                    trapBlocks.add(tempBlock);
                }

                // Register moving Blocks
                ArrayList<GameObject> movingBlocks = new ArrayList<>();
                for (int[] j : gameLevel.movingBlockCoords()) {
                    MovingBlock movingBlock = new MovingBlock(j[0], j[1], ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                    movingBlocks.add(movingBlock);
                    movingBlock.setMovement(() -> {
                        Game.player.removeObstructions(movingBlock.getArea());
                        movingBlock.moveX(-6);
                        Game.player.addExtraObstructions(movingBlock.getArea());
                    });
                    handler.addObject(movingBlock);
                    Game.player.addExtraObstructions(movingBlock.getArea());
                }

                // Register trigger blocks
                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 100, 1, 152, this, 2, 2, 6, 16);
                runnable = () -> {
                    for(GameObject gameObject : movingBlocks) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 1, 1, this, 4, 1, 6, 16);
                runnable = getTrapBlockRunnable(trapBlocks, 4);
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_C_V -> {
                // Register moving Blocks
                ArrayList<GameObject> movingBlocks = new ArrayList<>();
                ArrayList<GameObject> movingBlocksGate = new ArrayList<>();
                int c = 0;
                for (int[] j : gameLevel.movingBlockCoords()) {
                    MovingBlock movingBlock = new MovingBlock(j[0], j[1], ID.MovingBlock, Game.gameImages.movingBlockImages()[0]);
                    if(c > 151) {
                        movingBlocksGate.add(movingBlock);
                    } else {
                        movingBlock.setMovement(() -> {
                            Game.player.removeObstructions(movingBlock.getArea());
                            movingBlock.moveX(-6);
                            Game.player.addExtraObstructions(movingBlock.getArea());
                        });
                        movingBlocks.add(movingBlock);
                    }
                    handler.addObject(movingBlock);
                    Game.player.addExtraObstructions(movingBlock.getArea());
                    c++;
                }

                // Register trigger blocks
                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 100, 1, 144, this, 2, 2, 6, 16);
                runnable = () -> {
                    for(GameObject gameObject : movingBlocks) {
                        if(gameObject instanceof MovingBlock movingBlock) movingBlock.getMovement().run();
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, 199, this, 0,1,6,6);
                runnable = new Runnable() {
                    int iterations = 0;
                    @Override
                    public void run () {
                        for (GameObject gameObject : movingBlocksGate) {
                            if (gameObject instanceof MovingBlock movingBlock) {
                                if (iterations > 193) {
                                    Game.player.removeObstructions(movingBlock.getArea());
                                    movingBlock.moveY(24);
                                    Game.player.addExtraObstructions(movingBlock.getArea());
                                }
                                if (iterations < 18) {
                                    Game.player.removeObstructions(movingBlock.getArea());
                                    movingBlock.moveY(-24);
                                    Game.player.addExtraObstructions(movingBlock.getArea());
                                }
                                iterations++;
                            }
                        }
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_D_I, LEVEL_D_II, LEVEL_D_III, LEVEL_D_IV -> {
                // Register Spike Blocks
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                    tempBlock.switchImage();
                }
            }
            case LEVEL_D_V -> {
                // Register Spike Blocks
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                    tempBlock.switchImage();
                }

                Game.player.setCancelRIGHT(true);
                Game.player.setCancelLEFT(true);

                // Register moving trap Blocks
                ArrayList<GameObject> leftBlocks = new ArrayList<>();
                ArrayList<GameObject> rightBlocks = new ArrayList<>();
                int c = 0;
                List<Integer> leftPos = List.of(0, 1, 2, 6, 7, 8, 12, 13, 14, 18, 19, 20, 24, 25, 26, 30, 31, 32);
                for (int[] j : gameLevel.trapBlockCoords()) {
                    TrapBlock finalTempBlock = new TrapBlock(j[0], j[1], ID.MovingBlock, Game.gameImages.trapBlockImages()[0]);
                    if(leftPos.contains(c) || c >= 36) {
                        leftBlocks.add(finalTempBlock);
                    }
                    else {
                        rightBlocks.add(finalTempBlock);
                    }
                    handler.addObject(finalTempBlock);
                    Game.player.addExtraObstructions(finalTempBlock.getArea());
                    c++;
                }

                // Register trigger blocks
                TriggerBlock triggerBlock;
                Runnable runnable;

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(2)[0], gameLevel.triggerBlockCoords().get(2)[1],
                        ID.TriggerBlock, 60, 1, 2, this, 1, 1, 5, 3);
                runnable = () -> {
                    for(GameObject gameObject : leftBlocks) {
                        if(gameObject instanceof TrapBlock trapBlock) {
                            Game.player.moveX(1);
                            Game.player.setPressRIGHT(true);
                            trapBlock.moveX(24);
                        }
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 60, 1, 2, this, 1, 1, 5, 3);
                runnable = () -> {
                    for(GameObject gameObject : rightBlocks) {
                        if(gameObject instanceof TrapBlock trapBlock) {
                            Game.player.setPressLEFT(true);
                            trapBlock.moveX(-24);
                        }
                    }
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);

                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 40, 1, 1, this, 1, 1, 5, 3);
                runnable = () -> {
                    Game.player.setPressLEFT(false);

                    Game.player.setCancelRIGHT(true);
                    Game.player.setCancelLEFT(true);
                };
                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


            }
            case LEVEL_E_I -> {
                Game.player.SPEED_X = -5;
                Game.player.resetPhysics(true);


                // Register Spike Blocks
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                }
            }
            case LEVEL_E_II -> {
                Game.player.SPEED_X = -5;
                Game.player.resetPhysics(true);

                // Register Spike Blocks
                ArrayList<SpikeBlock> spikes = new ArrayList<>();
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                    spikes.add(tempBlock);
                }

                // Register Trigger Blocks

                TriggerBlock triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().getFirst()[0], gameLevel.triggerBlockCoords().getFirst()[1],
                        ID.TriggerBlock, 0, 1, 5, this, 3, 2, 2, 3);

                Runnable runnable = () -> {
                    int c = 0;
                    for(SpikeBlock spike : spikes) {
                        if(c == 2) spike.moveX(-24);
                        if(c == 3) spike.moveX(-24);
                        c++;
                    }
                };

                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);


                triggerBlock = new TriggerBlock(gameLevel.triggerBlockCoords().get(1)[0], gameLevel.triggerBlockCoords().get(1)[1],
                        ID.TriggerBlock, 0, 1, 5, this, 1, 0, 2, 1);

                runnable = () -> {
                    int c = 0;
                    for(SpikeBlock spike : spikes) {
                        if(c == 4) spike.moveX(24);
                        if(c == 5) spike.moveX(24);
                        c++;
                    }
                };

                triggerActions.put(triggerBlock, runnable);
                handler.addObject(triggerBlock);
            }
            case LEVEL_E_III -> {
                Game.player.SPEED_X = -5;
                Game.player.SPEED_Y = 40;
                Game.player.resetPhysics(true);

                // Register Spike Blocks
                int c = 0;
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    if(c++ <= 7) {
                        SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                        handler.addObject(tempBlock);
                        tempBlock.arise();
                        tempBlock.invert();
                    }
                    else {
                        SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                        handler.addObject(tempBlock);
                        tempBlock.arise();
                    }
                }
            }
            case LEVEL_E_IV -> {
                Game.player.SPEED_X = -5;
                Game.player.setCancelUP(true);
                Game.player.resetPhysics(true);

                // Register Spike Blocks
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                    tempBlock.setCancelUP(false);
                }
            }
            case LEVEL_E_V -> {
                Game.player.SPEED_X = -5;
                Game.player.resetPhysics(true);

                // Register Spike Blocks
                for(int[] j : gameLevel.spikeBlockCoords()) {
                    SpikeBlock tempBlock = new SpikeBlock(j[0], j[1], ID.Spike, Game.gameImages.spikeBlockImages(), handler);
                    handler.addObject(tempBlock);
                    tempBlock.arise();
                }

                for(int[] j : gameLevel.triggerBlockCoords()) {
                    TriggerBlock triggerBlock = new TriggerBlock(j[0], j[1],
                            ID.TriggerBlock, 0, 1, this, -1, 1, 16, 16);

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            SoundFXLoader sound = new SoundFXLoader();
                            sound.playSound("/sounds/Power Up S.mp3");
                            Game.player.SPEED_X = -Game.player.SPEED_X;
                            Game.player.resetPhysics(true);
                            terminateTriggerTask(this);
                        }
                    };

                    triggerActions.put(triggerBlock, runnable);
                    handler.addObject(triggerBlock);
                }
            }
        }
    }

    private Runnable getTrapBlockRunnable(List<GameObject> traps, int s) {
        return new Runnable() {
            final int speed = s;

            @Override
            public void run() {
                int counter = 0;
                for (GameObject trap : traps) {
                    if (trap instanceof TrapBlock trapBlock) {
                        if (counter >= speed) {
                            break;
                        }
                        if (trapBlock.visible) counter++;
                        trapBlock.visible = false;
                    }
                }
                if (counter == 0) terminateTriggerTask(this);
            }
        };
    }

    public void scheduleTriggerTask(TriggerBlock triggerBlock) {
        String caller = Thread.currentThread().getStackTrace()[2].getClassName();
        if(!caller.equals(TriggerBlock.class.getName())) return;
        if(!triggerActions.containsKey(triggerBlock)) return;
        handler.scheduledTasks.put(triggerActions.get(triggerBlock), new Handler.Clock(triggerBlock.getDelay(), triggerBlock.getSpeed(), triggerBlock.getIterations()));
        triggerActions.remove(triggerBlock);
    }

    public void scheduleTriggerTask(Runnable runnable) {
        String caller = Thread.currentThread().getStackTrace()[2].getClassName();
        if(!caller.equals(ButtonBlock.class.getName())) return;
        handler.scheduledTasks.put(runnable, new Handler.Clock(0, 2, 400));
    }

    public void terminateTriggerTask(Runnable runnable) {
        handler.scheduledTasks.remove(runnable);
    }

}
