# AI-Assisted Coding Documentation

## Tools Used
**ChatGPT (GPT-5)**: Used to help write unit tests, refactor code and explain error messages.

## Description of AI Assistance
1. **FindCommand Unit Tests**
   - Used ChatGPT to generate some test cases for `FindCommand`.
   - Suggested edge cases such as:
     - Long input strings
     - keyword with only spaces
   - Helped to identify potential pitfalls in `FakeTask` and `FakeTaskManager` for testing fuzzy search.

2. **Explain error messages**
    - Used ChatGPT to interpret executions like `IllegalArgumentException`.
    - Helped understand the design decisions behind validation in `TaskMatcher`.
3. **Code Refactoring**
    - Suggested improvement based on the code quality considerations.
    - Helped simplify tests, improve readability, and ensure compliance with coding standards.

## Observations
- AI tools are effective in generating boilerplate code and suggesting edge cases.
- AI often speeds up repetitive tasks such as checking for compliance of coding standard due to its rule-based.
- The output from AI is generally correct syntactically, but logical correctness and suitability requires human 
  review.
- Using AI made it easier to explore unusual inputs without manually brainstorming every case.

## Limitations
- AI cannot replace human understanding of the code logic; review and validation are essential.
- Some AI-suggested test cases or refactoring needed adjustments to fit actual implementation constraints.
- AI may propose plausible solutions that do not fully align with project-specific design decisions.

## Learning Outcome
- Learned to use AI as a collaborator rather than a replacement for coding skills.
- Learned how to integrate AI assistance responsibly while maintaining code quality and comprehension.
