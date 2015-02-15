package ScEventPlayer

/**
 * Created by steve on 2/8/15.
 */
abstract class Aggregate[TModel <: Aggregate[TModel]] (val id : AggregateId[TModel], var latestVersion : Int)
