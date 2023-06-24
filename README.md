# Snake Game

This is a simple console-based Snake game implemented in Java. The game allows the player to control a snake using the keyboard arrow keys (W, A, S, D) and navigate it within a grid. The objective is to eat apples, represented by the letter 'O', which appear randomly on the grid. As the snake eats apples, it grows longer. The game ends if the snake collides with the boundaries of the grid or with its own body.

## How to Play

1. Compile and run the `SnakeGame` class, either through an integrated development environment (IDE) or by executing the compiled Java bytecode.

2. Use the following keys to control the snake:
   - W: Move the snake upwards
   - A: Move the snake to the left
   - S: Move the snake downwards
   - D: Move the snake to the right

3. The snake will move continuously in the direction specified until a new direction is chosen or the game ends.

4. The objective is to eat as many apples as possible without colliding with the boundaries or the snake's body.

5. The game ends when the snake collides with a boundary or itself. The final score will be displayed.

## Implementation Details

The Snake Game is implemented using the following components:

- `SnakeGame` class: The main class that coordinates the game logic, including initializing the game, handling user input, updating the snake's position, generating apples, and checking for collisions.

- `SnakeDirections` enum: Defines the four possible directions in which the snake can move: up, down, left, and right.

- `snakePositions` ArrayList: Stores the positions of the snake's body segments as arrays of two integers (x, y).

- `grid` array: Represents the game grid, where each cell can contain a snake segment, an apple, or be empty.

- `score` variable: Keeps track of the player's score, which increments each time an apple is eaten.

- `speed` variable: Determines the speed of the game, controlling the delay between each movement of the snake.

## Additional Information

- The game grid has a fixed width of 50 and a height of half the width.

- The snake is initially positioned at the center of the grid and consists of several segments.

- The snake's movement is based on updating the positions of its segments. The head is moved in the specified direction, and the rest of the segments follow.

- When an apple is eaten, a new one is randomly generated on the grid, ensuring it does not overlap with the snake's body.

- The game ends if the snake collides with the boundaries or with its own body. The final score is displayed, indicating how many apples were eaten.

- The game interface is presented in the console, using ASCII characters to represent the snake, apples, and grid.

## Development Environment

- Language: Java
- Development Environment: Any Java IDE or a command-line Java development setup

## Acknowledgments

This Snake Game implementation is a simplified version and serves as a starting point for further enhancements or modifications. Feel free to explore and extend the code to add more features and improve the gameplay experience.

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
