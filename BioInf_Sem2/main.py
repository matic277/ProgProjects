import gene
#import sklearn

def readFile():
    filepath = 'genes.txt'

    with open(filepath) as f:
        lines = f.readlines()
    lines = [x.strip() for x in lines]

    geneClasses = []

    for i in range(len(lines)):
        l = lines[i]
        if "\t" not in l:
            batch = []
            for j in range(7):
                batch.append(lines[i + j + 1])
            #print(l)
            #print(batch)
            geneClasses.append(gene.GeneClass(l, batch))

    return geneClasses

geneClasses = readFile()
# print("ALL GENES READ:")
# for gc in geneClasses:
#     gc.printAll()

# r = geneClasses[0].getSequenceById("9913")
# print(r.seq)

a = "A"
if a == "-":
    print("yes")
else:
    print("no")

dm = []
for g in geneClasses:
    m = g.getAlignmentMatrix()
    # create matrix of [id, distance_matrix]
    dm.append([g.id, m])
    print("id: ", g.id)
    print("dm: ")
    for l in m:
        print("  ", l)
    print("")

# dm = [[['123'], [[1, 2],[3, 4]]], [['abc'], [[6, 7],[8, 9]]]]
#
# for m in dm:
#     print("id: ", m[0])
#     print("dm: ")
#     for l in m[1]:
#         print("  ", l)
#     print("\n")


print(" -> DONE.")
# for l in r2:
#     print(l)

# AglomerateCluster from scipy?



