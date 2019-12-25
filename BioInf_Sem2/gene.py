import globAlign
import jc

class GeneClass:
    def __init__(self, id, strs):
        self.id = id
        self.genes = []
        #print("Id: ", id)
        #print("strs: ", strs)
        for s in strs:
            #print("s: ", s)
            tokens = s.split("\t")
            self.genes.append(Gene(tokens[0], tokens[1], tokens[2]))

    def printAll(self):
        print("-> ClassId: " + self.id)
        for g in self.genes:
            g.print()

    def getSequenceById(self, id):
        for g in self.genes:
            if g.id == id:
                return g

    def getSequencesByName(self, name):
        seqs = []
        for g in self.genes:
            if g.name == name:
                seqs.append(g)
        return seqs

    def getAlignmentMatrix(self):
        m = self.get2dMatrix()
        #i = j = 0

        for i in range(len(self.genes)):
            for j in range(len(self.genes)):
                if (j > i) & (self.genes[i].id != self.genes[j].id):
                    #print(i, " ", j)
                    ga = globAlign.alignSequences(self.genes[i].seq, self.genes[j].seq)
                    m[i][j] = ga[0]
                    # TODO: correct with JC model
                    m[i][j] = jc.calculate_jukes_cantor(ga[1], ga[2])


        return m

    def get2dMatrix(self):
        m = []
        for i in range(len(self.genes)):
            subm = [0 for i in range(len(self.genes))]
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