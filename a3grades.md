# CS349 A3
Student: b99chen
Marker: Mingkun Yu


Total: 90 / 100 (90.00%)

Code: 
(CO: won’t compile, CR: crashes, FR: UI freezes/unresponsive, NS: not submitted)


Notes:   

## Deliverables (5%)

1. [5 /5] Typing "make run" compiles and launches the application.

## Basic functionality (15%)

2. [2 /2] There exists a menu bar at the top of the window.

3. [4 /4] The menu bar contains the menu "File", with the items "Reset" and "Quit", each using the appropriate accelerator keys.

4. [4 /4] The "Reset" menu option resets the ragdoll's position entirely to the default.

5. [5 /5] A ragdoll is visible, and is assembled in the rough shape of the doll.

## Translation (15%)

6. [5 /5] The ragdoll can be translated.

7. [ 5/5] The ragdoll can be translated with direct manipulation on the torso, with movements that match the mouse's movement one-to-one.

8. [5 /5] When the ragdoll is translated by the torso, the child elements of the torso are translated simultaneously.

## Rotation (20%)

9. [5 /5] All body parts of the ragdoll can be rotated, except for the torso, which does not rotate.

10. [5 /5] The parts can be rotated with direct manipulation on the body part, with rotations that match the mouse's rotation about the pivot one-to-one.

11. [5 /5] When the body parts are rotated, the child elements of the body part are rotated simultaneously.

12. [ 5/5] The appropriate body parts have limited rotation, relative to the rotation of their parent element.
* The upper arm is attached to the torso and may rotate an entire 360 degrees about its point of attachment to the torso.
* The lower arm should have a movement range of 135 degrees in either direction relative to the primary axis defined by the upper arm.
* The hand can pivot 35 degrees in either direction relative to the lower arm.
* The upper leg can pivot 90 degrees in either direction relative to the primary axis defined by the torso.
* The lower leg can also pivot 90 degrees in either direction relative to primary axis defined by the upper leg.
* The feet should be attached at a 90 degree angle to the lower leg and they can pivot 35 degrees in either direction from this initial orientation.

## Scaling (20%)

13. [5 /5] The legs of the ragdoll can be scaled, and no other body parts can be.

14. [ 5/5] The legs can be scaled with direct manipulation, with scaling that matches the mouse's distance from the pivot.

15. [ 5/5] When the upper legs are scaled, the lower legs are scaled as well, but the feet are not scaled.

16. [ 5/5] The legs scale along their primary axis.

## Robustness (15%)

17. [ 5/5] The legs can be properly rotated and scaled at the same time. 

18. [ 2/2] Grabbing a body part for direct manipulation does not cause the body part to change at all until the mouse begins to move.

19. [5 /5] The movement of body parts is smooth while they're being directly manipulated within constraints. (It is acceptable for the body part to "pop" if you move past one constraint and back into range.)

20. [3 /3] When a body part is manipulated, the child elements of the body part are also updated smoothly.


## Enhancements (10%)

21. [0 /10] Enhancement implemented.
