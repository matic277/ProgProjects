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
#     m = g.getAlignmentMatrix()
#     # create matrix of [id, distance_matrix]
#     dm.append([g.id, m])
#     print("id: ", g.id)
#     print("dm: ")
#     for l in m:
#         print("  ", l)
#     print("")
#
# print(" -> DONE.")

genemtx = []

# reading precomputed matrices
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


dndgram.plotAll(genemtx)

print("done")







