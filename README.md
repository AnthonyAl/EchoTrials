# Echo Trials - README

## Introduction
Echo Trials is a unique 2D Java-based platformer that challenges your intuition, memory, and patience. While it may seem straightforward at first, this game is anything but ordinary. Each level introduces a distinct gimmick, teaching players how to navigate specific obstacles and sharpening their problem-solving skills in preparation for the ultimate challenge.

The final level puts everything you’ve learned to the test, blending all previous mechanics and obstacles into one thrilling gauntlet. For those seeking an additional layer of excitement, Echo Trials features a Speedrun mode, where players can race against the clock to complete all levels with only three shared lives. Compete for the fastest completion time and prove your mastery of the game’s mechanics!

(Note: This game is a work in progress, and additional levels and features are planned to expand the experience even further.)

---

## Deep Dive

Diving into the details of Echo Trials, the player controls a small, tall figure capable of moving left, right, and jumping. The figure also has the ability to perform a wall jump, but only once before it must make contact with the ground again. In later stages, ice blocks are introduced, which cannot be used for wall jumps. Throughout the game, players will face challenges such as hidden pits, moving spikes, gravity and size alterations, as well as frozen environments that disrupt the figure’s movements, deviating from the player’s input.

The implemented levels currently introduce the following mechanics:

- **Pits**: Large gaps in the ground that require precise timing to jump over. Some pits appear unexpectedly to surprise the player.
- **Spikes**: Hazardous spikes that must be avoided, sometimes moving unpredictably to catch the player off guard.
- **Pushing Platforms**: Walls and platforms that may push the figure off cliffs or into dangerous obstacles like spikes.
- **Ice**: Slippery surfaces that disorient the player with reduced control over the figure's movements.
- **Movement Manipulation**: Levels where the player’s control over the figure’s movements is altered in surprising and challenging ways.

**Planned future updates** aim to introduce even more mechanics, including:

- **Size Alteration**: Challenges that require resizing the figure to navigate certain obstacles.
- **Moving Platforms**: Dynamic floors and walls that players must time and utilize to progress.
- **Teleportation**: Portals that teleport the figure to different areas, with destinations initially hidden from the player.

As previously mentioned, players must master these challenges before facing the final Bonus Level, which combines all the introduced concepts, or attempting the Speedrun Mode, a replayability feature designed for those eagerly awaiting new updates and fresh creative challenges.

---

## Stages of Development: Idea Analysis

Echo Trials draws its initial inspiration from the Spanish indie game *Level Devil*, a title known for its inventive level design and challenging mechanics. While the foundational ideas of *Level Devil* shaped the early stages of development, *Echo Trials* has significantly evolved to become a unique project with advanced features, creative mechanics, and technological improvements.

### The Role of Java: Beyond the Basics
The decision to use the Java programming language was made deliberately, with scalability and platform versatility in mind. Java’s cross-platform compatibility makes it an excellent choice for developing lightweight 2D games that can potentially run on various systems, including desktops and mobile devices. By building *Echo Trials* in Java, the project lays the groundwork for future mobile integration, opening up the possibility of reaching a wider audience. This forward-thinking approach ensures that *Echo Trials* will remain adaptable as it continues to grow and evolve.

### Advancing Graphics and Visual Fidelity
One area where *Echo Trials* has surpassed its inspiration is in graphical fidelity. While *Level Devil* utilizes basic x1 resolution textures, *Echo Trials* features stunning x48 resolution textures, offering much sharper visuals and a cleaner aesthetic. This dramatic improvement enhances the overall presentation, making environments, characters, and obstacles more visually appealing and immersive.

In addition to texture quality, a custom particle engine has been developed as part of *Echo Trials*. This particle engine is a significant technological achievement, adding dynamic visual effects that elevate the game’s atmosphere and gameplay. The engine already supports effects such as:

- **Friction**: Particles that simulate surfaces like grass, stone, or sand when the figure interacts with them.
- **Wall Jumping**: A burst of particles to visually emphasize the action of jumping off a wall.
- **Slipping on Ice**: Unique particle trails that illustrate the slippery nature of frozen environments.
- **Spike Explosions**: A sharp burst of particles when the figure hits spikes, adding an impactful and satisfying visual cue.

This robust particle engine also lays the groundwork for future mechanics. Planned updates will introduce particle-based obstacles such as fire that burns or water that slows the figure down. These elements will further enrich the gameplay experience, making *Echo Trials* both visually engaging and mechanically diverse.

### Sound Design: A Personal Touch
Sound design in *Echo Trials* also represents a leap forward. Unlike *Level Devil*, which uses simple stock sounds, *Echo Trials* features custom audio sampled and crafted by the developer, "Antitonius." This bespoke approach ensures that each sound effect aligns perfectly with the gameplay, enhancing immersion. From the subtle scrape of ice to the satisfying thud of a successful jump, every sound has been designed to heighten the player’s experience.

### Combining Vision and Innovation
The decision to improve the graphics, sound design, and technical foundation reflects the creative vision behind *Echo Trials*. While it acknowledges the inspiration from *Level Devil*, this project builds upon those ideas to create a richer, more polished, and immersive experience. By focusing on scalable development, innovative particle effects, and custom sound design, *Echo Trials* is positioning itself as more than just a game—it's a technical and artistic achievement in the making.

---

## Stages of Development: Design

The design philosophy behind *Echo Trials* revolves around delivering an experience that is both challenging and unpredictable. It seeks to constantly test the player’s intuition and readiness for the unexpected, all while encouraging creative thinking. The game takes the familiar platformer format and turns it on its head, surprising players in new and innovative ways while retaining a level of fairness that rewards adaptability and skill.

### Designing the Unexpected
At its core, *Echo Trials* strives to disrupt the player's expectations at every turn. Even fundamental aspects of the gameplay design are subverted. For instance, the HP hearts UI decreases by two hearts upon death instead of the standard single heart, creating an additional layer of tension for players. Similarly, traditional platformer mechanics like spikes or pits are modified to behave unpredictably. A standout example is the jumping spikes, which activate when the player presses the jump button, reversing the player’s usual approach of avoiding spikes through timing and agility.

Another unconventional aspect of *Echo Trials* lies in its obstacle design. While the game does include standard elements like moving platforms and slippery ice, these features often come with a twist, designed to force the player to think outside the box. For example, ice blocks may not allow for wall jumping, and some platforms may push the player into hazards instead of helping them navigate the terrain. These design choices reinforce the idea that players must stay alert, ready to adapt their strategies on the fly.

### Empowering Mobility
One of the standout differences between *Echo Trials* and its inspiration, *Level Devil*, is the figure’s enhanced mobility. Players can move left or right, jump, and even execute a single wall-jump before needing to touch the ground. This expanded movement set gives players a greater sense of control, fostering an initial feeling of safety and mastery. However, this sense of security is deliberately challenged as players face increasingly unpredictable obstacles. Despite its deceptive nature, the mobility system proves crucial for players who master the levels, particularly in the Speedrun mode, where quick and precise movements are essential to securing the fastest completion times.

### Level Structure and Progression
The game is divided into levels, with each level containing five challenges that share a total of three lives. This structure adds a layer of pressure, as players must balance caution and boldness while progressing through increasingly difficult challenges.

Each level introduces a unique theme designed to train players in a specific mechanic or prepare them for a specific type of surprise. These themes gradually increase in difficulty, ensuring that players are constantly learning and adapting. For example:

- **Level 1**: Pits and Spikes – Players encounter basic platforming mechanics, where they must navigate hidden pits and avoid static or moving spikes.
- **Level 2**: Ice and Slippery Surfaces – Challenges the player's control over their figure by introducing ice blocks that make movement unpredictable and disallow wall-jumping.
- **Level 3**: Pushing and Moving Platforms – Introduces platforms that may help or hinder, requiring players to assess and anticipate their movements.
- **Level 4**: Movement Alteration – Messes with the player's sense of control, changing movement speed or inverting controls.

The final bonus level is a culmination of all the previously introduced mechanics and obstacles, blending them into a chaotic and demanding trial that requires mastery of all prior challenges.

### Sound and Graphical Design
As mentioned in the earlier analysis, *Echo Trials* makes significant strides in graphical fidelity and sound design, further enriching the player experience.

#### **Graphics**:
- The game's texture resolution is x48, a remarkable improvement over *Level Devil*’s x1 resolution textures. This increase in detail brings the world of *Echo Trials* to life, with sharper visuals and more polished aesthetics. The environments, characters, and obstacles feel more refined, making the gameplay experience more immersive.
- Additionally, the custom particle engine is a central feature of the game’s graphical design. The engine allows for dynamic particle effects, such as:
    - Wall-jump particles that emphasize the figure’s movements.
    - Ice slip particles that visually communicate the slippery effect of frozen surfaces.
    - Spike explosions that provide dramatic feedback upon failure.
    - Future mechanics like fire or water-based obstacles are already planned, leveraging the robust capabilities of the particle engine to introduce visually stunning challenges.

#### **Sound**:
- The sound design in *Echo Trials* is equally thoughtful, with custom audio sampled directly by the developer, "Antitonius." Every sound effect—from the crunch of ice to the satisfying thud of a successful wall jump—has been crafted to perfectly match the game’s mechanics. The sound design not only enhances immersion but also serves as an important gameplay cue, helping players respond to obstacles intuitively.

---

## Conclusion
The design of *Echo Trials* is a deliberate effort to create a platformer that is unconventional, surprising, and deeply engaging. Its blend of enhanced mobility, unpredictable mechanics, and high-quality visuals and sound makes for an experience that continually challenges players to think differently. By pushing boundaries in both gameplay and presentation, *Echo Trials* offers an experience that is as fun as it is thought-provoking.