package main

import (
	"fmt"
	"math/rand"
	"os"
	"strings"
	"sync"
	"time"
)

func gardener(garden [][]string, m *sync.Mutex) {
	for {
		m.Lock()
		for i := 0; i < 5; i++ {
			for j := 0; j < 5; j++ {
				if garden[i][j] == "0" {
					garden[i][j] = "1"
				}
			}
		}
		m.Unlock()
		time.Sleep(2100 * time.Millisecond)
	}
}

func nature(garden [][]string, m *sync.Mutex) {
	rand.Seed(time.Now().UTC().UnixNano())
	for {
		m.Lock()
		var number= rand.Intn(5)
		garden[number][rand.Intn(5)] = "0"
		m.Unlock()

		time.Sleep(1000 * time.Millisecond)
	}
}

func monitor1(garden [][]string, m *sync.Mutex) {
	file, err := os.Create("matrix.txt")

	if err != nil{
		fmt.Println("Unable to create file:", err)
		os.Exit(1)
	}
	defer file.Close()

	for {
		m.Lock()
		for i := 0; i < 5; i++ {
			line := strings.Join(garden[i][:],"")
			file.WriteString(line + "\n")
		}
		m.Unlock()
		file.WriteString("\n\n\n")
		time.Sleep(1000 * time.Millisecond)
	}
}

func monitor2(garden [][]string, m *sync.Mutex) {
	for {
		m.Lock()
		for _, i := range garden {
			for _, j := range i {
				print(j)
			}
			println()
		}
		m.Unlock()
		println()
		time.Sleep(2000 * time.Millisecond)
	}
}

func main() {
	var garden [][]string
	var wg sync.WaitGroup
	var m sync.Mutex

	for j := 0; j < 5; j++ {
		var row []string
		for i := 0; i < 5; i++ {
			row = append(row, "1")
		}
		garden = append(garden, row)
	}

	wg.Add(4)
	go nature(garden, &m)
	go gardener(garden, &m)
	go monitor1(garden, &m)
	go monitor2(garden, &m)
	wg.Wait()
}
