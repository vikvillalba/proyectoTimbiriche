# Timbiriche
 
> A multiplayer Dots and Boxes ("Timbiriche") game built in **Java**, with a **Swing** desktop UI and a **custom event-driven communication layer** built on raw **Java Sockets**.
 
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apachemaven&logoColor=white)
![Swing](https://img.shields.io/badge/UI-Java%20Swing-blue?style=flat)
![Sockets](https://img.shields.io/badge/Networking-Java%20Sockets-4B8BBE?style=flat)
![Event-Driven](https://img.shields.io/badge/Architecture-Event--Driven-6C63FF?style=flat)
 
---
## Overview
 
**Timbiriche** (known internationally as *Dots and Boxes*) is a classic pencil-and-paper game where players take turns drawing lines between dots to complete boxes — whoever completes the most boxes wins. This project brings the game online as a **multiplayer desktop application**, built entirely in **Java**.
 
The standout part of this project is the **networking layer**: instead of relying on an existing messaging library, the **EventBus** was implemented from scratch on top of low-level **Java Sockets**. Player actions (drawing a line, completing a box, ending a turn) are serialized and sent over the socket connection as events, then dispatched to subscribers on each connected client, keeping every player's board in sync in real time.

## Architecture
 
The core design decision behind this project is a **custom, low-level event-driven communication model**:
 
- Each player action is emitted as an **event** rather than calling remote logic directly.
- A hand-built **EventBus**, backed by raw **TCP sockets**, handles sending, receiving, and dispatching these events between clients.
- This decouples the **Swing** game UI and game logic from the networking implementation, making it possible to evolve either side independently.
- Building the messaging layer at the socket level (rather than using a higher-level framework) meant handling connection management, serialization, and event dispatch manually.

## Tech Stack
 
- **Language:** Java
- **Build tool:** Maven
- **UI:** Java Swing (desktop client)
- **Networking / EventBus:** Custom implementation over low-level Java Sockets (TCP)
- **Design/modeling:** [StarUML](https://staruml.io/) (`timbiricheShark.mdj`) for class diagrams

## Key Takeaways
 
- Building a **custom event-driven communication layer (EventBus)** from scratch using low-level **Java Sockets**.
- Handling **TCP connection management, serialization, and event dispatch** manually, without relying on higher-level frameworks.
- Building a **multiplayer desktop UI** with Java Swing.
- Synchronizing **real-time game state** across multiple connected clients.
- Modeling the system design with **UML class diagrams** before implementation.

 
