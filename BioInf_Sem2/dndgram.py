import numpy as np
from matplotlib import pyplot as plt
from scipy.cluster.hierarchy import dendrogram
from sklearn.cluster import AgglomerativeClustering

def getLinkageMtx(model):
    counts = np.zeros(model.children_.shape[0])
    n_samples = len(model.labels_)
    for i, merge in enumerate(model.children_):
        current_count = 0
        for child_idx in merge:
            if child_idx < n_samples:
                current_count += 1  # leaf node
            else:
                current_count += counts[child_idx - n_samples]
        counts[i] = current_count

    return np.column_stack([model.children_, model.distances_, counts]).astype(float)


def plotDendrogram(linkeageMtx, **kwargs):
    # Plot the corresponding dendrogram
    dendrogram(linkeageMtx, **kwargs)
    # plt.xlabel("TODO")
    plt.subplots_adjust(right=0.7)
    plt.show()

def plotAndOrderAll(data):
    names = []
    names.append("Bos taurus")
    names.append("Mus musculus")
    names.append("Homo sapiens")
    names.append("Danio rerio")
    names.append("Canis lupus familiaris")
    names.append("Rattus norvegicus")
    names.append("Macaca mulatta")

    # setting distance_threshold=0 ensures we compute the full tree.
    # model = AgglomerativeClustering(distance_threshold=0, n_clusters=None)

    # UPGMA algorithm (agglomerative clustering, average linkage, precomputed distances)
    model = AgglomerativeClustering(distance_threshold=0, n_clusters=None, affinity='precomputed', linkage='average')

    # create an array of models
    # and linkeage matrices
    # -> [[id, model, lm], ...]
    dataset = []
    for e in data:
        elt = []
        elt.append(e[0])
        model = model.fit(e[1])
        elt.append(model)
        elt.append(getLinkageMtx(model))
        dataset.append(elt)

    print("before ordering:")
    for elt in dataset:
        print(elt[0])


    # order dataset (linkeage matrices) by diff in last two elements
    ordered = []
    for j in range(len(dataset)):
        for i in range(len(dataset)-1):
            # set[i]
            set = dataset[i]
            lm1 = set[2]
            last1 = lm1[len(lm1)-1]
            secondlast1 = lm1[len(lm1)-2]
            diff1 = last1[2] - secondlast1[2]

            # set[i+1]
            set = dataset[i+1]
            lm2 = set[2]
            last2 = lm2[len(lm2) - 1]
            secondlast2 = lm2[len(lm2) - 2]
            diff2 = last2[2] - secondlast2[2]

            #print(diff1, " > ", diff2)
            # swap
            if diff2 > diff1:
                dataset[i], dataset[i+1] = dataset[i+1], dataset[i]

    for i in range(len(dataset)):
        # set[i]
        set = dataset[i]
        lm1 = set[2]
        last1 = lm1[len(lm1) - 1]
        secondlast1 = lm1[len(lm1) - 2]
        diff1 = last1[2] - secondlast1[2]
        print(set[0], " ", diff1)


    print("after ordering:")
    for elt in dataset:
        print(elt[0])


    # show plots
    # dataset   ->  [[id, model, lm], ...]
    # datapoint ->  [id, model, lm]
    for datapoint in dataset:
        # print(datapoint)
        # model = model.fit(datapoint[1])
        plt.title("Phylogenetic tree, Gene-set id: " + datapoint[0])

        # plot the top three levels of the dendrogram
        # plot_dendrogram(model, truncate_mode='level', p=3, labels=names, orientation="left")

        lm = datapoint[2]
        plotDendrogram(lm, truncate_mode='level', p=3, labels=names, orientation="left")



    # for elt in data:
    #     model = model.fit(elt[1]) # elt[1] or elt.distMtx
    #     plt.title("Hierarchical Clustering Dendrogram, Gene-set id: " + elt[0]) # elt[0] or elt.id
    #
    #     # plot the top three levels of the dendrogram
    #     # plot_dendrogram(model, truncate_mode='level', p=3, labels=names, orientation="left")
    #
    #     lm = getLinkageMtx(model)
    #     plotDendrogram(lm, truncate_mode='level', p=3, labels=names, orientation="left")
    #     #print(lm)
    #     plt.xlabel("Number of points in node (or index of point if no parenthesis).")
    #     plt.show()
