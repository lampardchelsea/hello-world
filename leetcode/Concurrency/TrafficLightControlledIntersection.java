/**
Refer to
https://leetcode.ca/2019-06-01-1279-Traffic-Light-Controlled-Intersection/
1279	Traffic Light Controlled Intersection

 There is an intersection of two roads.
     First road is road A where cars travel from North to South in direction 1 and from South to North in direction 2.
     Second road is road B where cars travel from West to East in direction 3 and from East to West in direction 4.


 There is a traffic light located on each road before the intersection. A traffic light can either be green or red.
     Green means cars can cross the intersection in both directions of the road.
     Red means cars in both directions cannot cross the intersection and must wait until the light turns green.

 The traffic lights cannot be green on both roads at the same time.
 That means when the light is green on road A, it is red on road B and when the light is green on road B,
 it is red on road A.

 Initially, the traffic light is green on road A and red on road B.
 When the light is green on one road, all cars can cross the intersection in both directions until the light becomes green on the other road.
 No two cars traveling on different roads should cross at the same time.

 Design a deadlock-free traffic light controlled system at this intersection.

 Implement the function void carArrived(carId, roadId, direction, turnGreen, crossCar) where:
     carId is the id of the car that arrived.
     roadId is the id of the road that the car travels on.
     direction is the direction of the car.
     turnGreen is a function you can call to turn the traffic light to green on the current road.
     crossCar is a function you can call to let the current car cross the intersection.

 Your answer is considered correct if it avoids cars deadlock in the intersection.
 Turning the light green on a road when it was already green is considered a wrong answer.


 Input: cars = [1,3,5,2,4], directions = [2,1,2,4,3], arrivalTimes = [10,20,30,40,50]
 Output: [
         "Car 1 Has Passed Road A In Direction 2",    // Traffic light on road A is green, car 1 can cross the intersection.
         "Car 3 Has Passed Road A In Direction 1",    // Car 3 crosses the intersection as the light is still green.
         "Car 5 Has Passed Road A In Direction 2",    // Car 5 crosses the intersection as the light is still green.
         "Traffic Light On Road B Is Green",          // Car 2 requests green light for road B.
         "Car 2 Has Passed Road B In Direction 4",    // Car 2 crosses as the light is green on road B now.
         "Car 4 Has Passed Road B In Direction 3"     // Car 4 crosses the intersection as the light is still green.
         ]

 Input: cars = [1,2,3,4,5], directions = [2,4,3,3,1], arrivalTimes = [10,20,30,40,40]
 Output: [
         "Car 1 Has Passed Road A In Direction 2",    // Traffic light on road A is green, car 1 can cross the intersection.
         "Traffic Light On Road B Is Green",          // Car 2 requests green light for road B.
         "Car 2 Has Passed Road B In Direction 4",    // Car 2 crosses as the light is green on road B now.
         "Car 3 Has Passed Road B In Direction 3",    // Car 3 crosses as the light is green on road B now.
         "Traffic Light On Road A Is Green",          // Car 5 requests green light for road A.
         "Car 5 Has Passed Road A In Direction 1",    // Car 5 crosses as the light is green on road A now.
         "Traffic Light On Road B Is Green",          // Car 4 requests green light for road B.
                                                         Car 4 blocked until car 5 crosses and then traffic light is green on road B.
         "Car 4 Has Passed Road B In Direction 3"     // Car 4 crosses as the light is green on road B now.
         ]

 Explanation: This is a dead-lock free scenario.
 Note that the scenario when car 4 crosses before turning light into green on road A
 and allowing car 5 to pass is also correct and Accepted scenario.

 Constraints:

 1 <= cars.length <= 20
 cars.length = directions.length
 cars.length = arrivalTimes.length
 All values of cars are unique
 1 <= directions[i] <= 4
 arrivalTimes is non-decreasing

 @tag-concurrency
*/

// Solution 1: Keyword synchronized for signal variable for thread control.
// Refer to
// http://leetcode.libaoj.in/traffic-light-controlled-intersection.html
class TrafficLight {
    private int canPassRoad = 1;
    public TrafficLight() {}
    public void carArrived(
        int carId,           // ID of the car
        int roadId,          // ID of the road the car travels on. Can be 1 (road A) or 2 (road B)
        int direction,       // Direction of the car
        Runnable turnGreen,  // Use turnGreen.run() to turn light to green on current road
        Runnable crossCar   // Use crossCar.run() to make car cross the intersection 
    ) {
        synchronized (this) {
            if (canPassRoad != roadId) {
                canPassRoad = roadId;
                turnGreen.run();
            }
            crossCar.run();
        }
    }
}

// Solution 2: We can use Semaphore or Mutex to instead of sychronized
// https://www.programmersought.com/article/13766695964/
/**
Method 1: Use semaphores
Main ideas:
(1) Use the semaphore to ensure the ownership of the intersection, and use the variable flag to identify whether the current path 
    was a green path before, if not, you need to call the truegreen function to change the street light to green , And adjust the 
    flag to the current path (because the current path is green);
(2) After the current car passes, the ownership will be released first;
*/
#include<semaphore.h>
class TrafficLight {
public:
	
    sem_t sem;
    int flag;//Path number identified as green light

    TrafficLight() {
        flag=1;
        sem_init(&sem,0,1);
    }

    void carArrived(
        int carId,                   // ID of the car
        int roadId,                  // ID of the road the car travels on. Can be 1 (road A) or 2 (road B)
        int direction,               // Direction of the car
        function<void()> turnGreen,  // Use turnGreen() to turn light to green on current road
        function<void()> crossCar    // Use crossCar() to make car cross the intersection
    ) {
        sem_wait(&sem);//Try to get the right to own the intersection first
        //If the current path is not the path with the green light before, adjust the street light and path
        if(flag!=roadId){
            turnGreen();
            flag=3-flag;
        }
        crossCar();
        sem_post(&sem);//Release ownership
    }
};

/**
Method 2: Use mutex
Main ideas:
(1) Here, since the initial value of the semaphore is 1, you can directly use the mutex instead of the semaphore;
*/
#include<pthread.h>
class TrafficLight {
public:

    pthread_mutex_t mutex;
    int flag;

    TrafficLight() {
        flag=1;
        pthread_mutex_init(&mutex,0);
    }

    void carArrived(
        int carId,                   // ID of the car
        int roadId,                  // ID of the road the car travels on. Can be 1 (road A) or 2 (road B)
        int direction,               // Direction of the car
        function<void()> turnGreen,  // Use turnGreen() to turn light to green on current road
        function<void()> crossCar    // Use crossCar() to make car cross the intersection
    ) {
        pthread_mutex_lock(&mutex);
        if(flag!=roadId){
            turnGreen();
            flag=3-flag;
        }
        crossCar();
        pthread_mutex_unlock(&mutex);
    }
};

