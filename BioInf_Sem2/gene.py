import globAlign
import jc

def sortArrayOfGeneClasses(a):
    ordered = []

    # order by geneset minmax value
    a.sort(key=lambda x: x.dMinMax, reverse=True)

    return a


class GeneClass:
    def __init__(self, id, strs):
        self.id = id
        self.genes = []
        # print("Id: ", id)
        # print("strs: ", strs)
        for s in strs:
            #print("s: ", s)
            tokens = s.split("\t")
            self.genes.append(Gene(tokens[0], tokens[1], tokens[2]))

    # def __init__(self, id, m):
    #     self.id = id
    #     self.distMtx = m

    def print1(self):
        print("-> ClassId: " + self.id)
        for g in self.genes:
            g.print()

    def print2(self):
        print("-> ClassId: " + self.id)
        print("-> DistMtx: ")
        for l in self.distMtx:
            print(l)


    def calculateMinMaxDist(self):
        # Danio rerio is 4th in matrix (3rd with 0)
        # mammals are all the rest...

        self.fishToAvgMammalsDist = 0
        self.avgMammalsDist = 0
        sumMammalsDist = 0
        sumFishDist = 0

        # calculate avg distance between mammals,
        # thats everything but 3rd row and column
        c1 = 0
        c2 = 0
        for i in range(len(self.distMtx)):
            for j in range(len(self.distMtx)):
                if j > i:
                    # mammals
                    if i != 3 and j != 3:
                        c1 += 1
                        sumMammalsDist += self.distMtx[i][j]
                    # fish to mammals
                    else:
                        c2 += 1
                        sumFishDist += self.distMtx[i][j]


        self.avgMammalsDist = sumMammalsDist / c1
        self.fishToAvgMammalsDist = sumFishDist / c2
        self.dMinMax = abs(self.fishToAvgMammalsDist - self.avgMammalsDist)


    def calculateAndGetAlignedDistanceMatrix(self):
        m = self.get2dMatrix()
        #i = j = 0

        for i in range(len(self.genes)):
            for j in range(len(self.genes)):
                if (j > i) & (self.genes[i].id != self.genes[j].id):
                    #print(i, " ", j)
                    ga = globAlign.alignSequences(self.genes[i].seq, self.genes[j].seq)
                    m[i][j] = ga[0]
                    # TODO: correct with JC model
                    jcRes = jc.getJukesCantorDistance(ga[1], ga[2])
                    m[i][j] = jcRes
                    #m[j][i] = jcRes

        self.distMtx = m
        return m

    def get2dMatrix(self):
        m = []
        for i in range(7):
            subm = [0 for i in range(7)]
            m.append(subm)
        return m


class Gene:
    def __init__(self, id, name, seq):
      self.id = id
      self.name = name
      self.seq = seq

    def print(self):
        print("  -> Id, Name: ", self.id, " ", self.name)
        print("  -> Sequence: ", self.seq)