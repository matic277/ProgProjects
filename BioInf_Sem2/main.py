import gene
import dndgram
#import sklearn

# reading and creating matrix from scratch
# def readFile():
#     filepath = 'genes.txt'
#
#     with open(filepath) as f:
#         lines = f.readlines()
#     lines = [x.strip() for x in lines]
#
#     geneClasses = []
#
#     for i in range(len(lines)):
#         l = lines[i]
#         if "\t" not in l:
#             batch = []
#             for j in range(7):
#                 batch.append(lines[i + j + 1])
#             #print(l)
#             #print(batch)
#             geneClasses.append(gene.GeneClass(l, batch))
#
#     return geneClasses
#
# geneClasses = readFile()
#
# dm = []
# for g in geneClasses:
#     m = g.calculateAndGetAlignedDistanceMatrix()
#     # create matrix of [id, distance_matrix]
#     dm.append([g.id, m])
#     print(g.id)
#     print()
#     for l in m:
#         print(l)
#
# print(" -> DONE.")





# reading precomputed matrices
genemtx = []
def readFile():
    filepath = "precomp_md_custom_jc.txt"

    with open(filepath) as f:
        lines = f.readlines()

    for i in range(0, len(lines), 8):
        id = lines[i].strip("\n")
        m = []
        for j in range(1, 8):
            #print(i+j)
            vals = lines[i+j]
            vals = vals.replace("[", "").replace("]", "").replace("\n", "").replace(",", "").replace("'", "")
            tokens = vals.split(" ")
            subm = []
            for t in tokens:
                subm.append(float(t))
            m.append(subm)
        genemtx.append([id, m])

readFile()

dndgram.plotAndOrderAll(genemtx)

print("done")







