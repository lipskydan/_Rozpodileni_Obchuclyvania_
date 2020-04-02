package main

import (
	"fmt"
	"sync"
	"time"
)

func Barber(visitors chan int) {
	for {
		fmt.Println("Barber is sleeping")
		<-visitors
		time.Sleep(time.Second)
		fmt.Println("Barber woke up and start work")
		time.Sleep(time.Second * 3)
		fmt.Println("Barber finish work")
	}
}

func Visitor(visitors chan int, wg *sync.WaitGroup, ind int, name string) {
	time.Sleep(time.Second)
	visitors<-ind
	fmt.Println("Visitor " + name +" came")
	time.Sleep(time.Second * 3)
	fmt.Println("Visitor " + name + " is done")
	wg.Done()
}

func main()  {
	var visitors = make(chan int)


	var wg sync.WaitGroup
	wg.Add(3)

	go Barber(visitors)
	go Visitor(visitors, &wg, 1, "Antonio")
	go Visitor(visitors, &wg, 2, "Bob")
	go Visitor(visitors, &wg, 3, "Clark")

	wg.Wait()
}
