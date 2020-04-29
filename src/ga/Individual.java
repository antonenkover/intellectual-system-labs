package ga;

import timetable_elements.Group;
import timetable_elements.Subject;
import timetable_elements.Timetable;

public class Individual {

    int[] chromosome;
    double fitness = -1;

    public Individual(Timetable timetable) {
        int numClasses = timetable.getNumOfClasses();
        int chromosomeLength = numClasses * 4;
        int[] newChromosome = new int[chromosomeLength];
        int chromosomeIndex = 0;
        for (Group group : timetable.getGroupsAsArray()) {
            for (int moduleId : group.getModuleIds()) {
                int timeslotId = timetable.getRandomTimeslot().getTimeslotId();
                newChromosome[chromosomeIndex] = timeslotId;
                chromosomeIndex++;
                int roomId = timetable.getRandomRoom().getRoomId();
                newChromosome[chromosomeIndex] = roomId;
                chromosomeIndex++;
                Subject module = timetable.getModule(moduleId);
                newChromosome[chromosomeIndex] = module.getRandomLecturerId();
                chromosomeIndex++;
                newChromosome[chromosomeIndex] = module.getRandomPractikId();
                chromosomeIndex++;
            }
        }

        this.chromosome = newChromosome;
    }

    public Individual(int chromosomeLength) {
        int[] individual;
        individual = new int[chromosomeLength];

        for (int gene = 0; gene < chromosomeLength; gene++) {
            individual[gene] = gene;
        }

        this.chromosome = individual;
    }

    public String toString() {
        String output = "";
        for (int gene = 0; gene < this.chromosome.length; gene++) {
            output += this.chromosome[gene] + " ";
        }
        return output;
    }

    public boolean containsGene(int gene) {
        for (int i = 0; i < this.chromosome.length; i++) {
            if (this.chromosome[i] == gene) {
                return true;
            }
        }
        return false;
    }

    public int[] getChromosome() {
        return this.chromosome;
    }

    public int getChromosomeLength() {
        return this.chromosome.length;
    }

    public void setGene(int offset, int gene) {
        this.chromosome[offset] = gene;
    }

    public int getGene(int offset) {
        return this.chromosome[offset];
    }

    public double getFitness() {
        return this.fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }


}
