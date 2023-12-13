# AILS-MCLP: A Hybrid Adaptive Iterated Local Search Heuristic for the Maximal Covering Location Problem

Adaptive Iterated Local Search (AILS) is an iterated local search-based meta-heuristic that embeds adaptive strategies to tune  diversity control parameters. These parameters are the perturbation degree and the acceptance criterion. They are key parameters to ensure that the method escapes from local optima and keeps an adequate level of exploitation and exploration of the method. This algorithm is an adaptation of the AILS-PR [1], but applied to the Maximal Covering Location Problem. Its implementation is in JAVA language. This job was submitted to International Transactions in Operational Research.

## References

Those interested in using any part of this algorithm in academic works must cite the following references:

[1] Máximo, V. R., Cordeau, J. F.,  Nascimento, M. C. (2023). 
A hybrid adaptive iterated local search heuristic for the maximal covering location problem. International Transactions in Operational Research.  https://doi.org/10.1111/itor.13387

[2] Máximo, Vinícius R., Nascimento, Mariá C.V. (2021).
A hybrid adaptive iterated local search with diversification control to the capacitated vehicle routing problem. European Journal of Operational Research, Volume 294, p. 1108-1119, https://doi.org/10.1016/j.ejor.2021.02.024 (also available at [aXiv](https://arxiv.org/abs/2012.11021)).

[3] Máximo, Vinícius R., Cordeau, Jean-François, Nascimento, Mariá C.V. (2022).
An adaptive iterated local search heuristic for the Heterogeneous Fleet Vehicle Routing Problem. Computers & Operations Research, Volume 148, p. 105954.
https://doi.org/10.1016/j.cor.2022.105954 (also available at [aXiv](https://arxiv.org/abs/2111.12821)).

## To run the algorithm

```console
java -jar AILS-PR.jar -file Instancias/NewYork.txt -limit 100 -best 1574256 -p 100 -r 400
```

Run the AILS-PR class that has the following parameters:

**-file** : Enter the file address of the problem instance.

**-limit** : Enter a value in seconds that will be used in the stopping criteria. The default value is Double.MAX_VALUE.

**-best** : Indicate the value of the optimal solution. The default value is Double.MAX_VALUE.

**-p** : The p value indicates the number of facilities to be installed. The default value is 50.

**-r** : The value of r indicates the measurement of the coverage radius in meters. The default value is 400.

**-gamma** : number of iterations for AILS-PR to perform a new adjustment of variable 𝜔. The default value is 30.

**-etaMin** : indicates the minimum value for the acceptance criterion η value. The default value is 0.1.

**-dBeta** : reference distance between the reference solution and the current solution obtained after the local search. The default value is 10.

**-sigma** : indicates the maximum cardinality of the elite set. The default value is 40.

## License

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)

- **[MIT license](https://opensource.org/licenses/MIT)**
- Copyright(c) 2022 Vinícius R. Máximo
