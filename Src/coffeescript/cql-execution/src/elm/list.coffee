{ Expression, UnimplementedExpression } = require './expression'
{ ValueSet } = require '../datatypes/datatypes'
{ build } = require './builder'
{ typeIsArray } = require '../util/util'

# Lists and Intervals

module.exports.List = class List extends Expression
  constructor: (json) ->
    super
    @elements = (build json.element) ? []

  exec: (ctx) ->
    (item.exec(ctx) for item in @elements)

module.exports.Exists = class Exists extends Expression
  constructor: (json) ->
    super

  exec: (ctx) ->
    @execArgs(ctx)?.length > 0

# TODO: Deconflict w/ definition in comparison.coffee
# module.exports.Equal = class Equal extends UnimplementedExpression

# TODO: Deconflict w/ definition in comparison.coffee
# module.exports.NotEqual = class NotEqual extends UnimplementedExpression

# TODO: Deconflict w/ definition in interval.coffee
module.exports.Union = class Union extends Expression
  constructor: (json) ->
    super

  exec: (ctx) ->
    # TODO: Support intervals
    @execArgs(ctx).reduce (x, y) -> x.concat y

# TODO: Spec has "Difference" defined, but should this be "Except"? (also deconflict w/ interval.coffee)
module.exports.Except = class Except extends UnimplementedExpression

# TODO: Deconflict w/ definition in interval.coffee
module.exports.Intersect = class Intersect extends Expression
  constructor: (json) ->
    super

  exec: (ctx) ->
    # TODO: Support intervals
    @execArgs(ctx).reduce (x, y) -> (itm for itm in x when itm in y)

module.exports.Times = class Times extends UnimplementedExpression

module.exports.Filter = class Filter extends UnimplementedExpression

module.exports.SingletonFrom = class SingletonFrom extends Expression
  constructor: (json) ->
    super

  exec: (ctx) ->
    arg = @execArgs ctx
    if arg.length > 1 then throw new Error 'IllegalArgument: \'SingletonFrom\' requires a 0 or 1 arg array'
    else if arg.length is 1 then return arg[0]
    else return null

module.exports.IndexOf = class IndexOf extends UnimplementedExpression

# TODO: Deconflict w/ definition in string.coffee
# module.exports.Indexer = class Indexer extends UnimplementedExpression

# TODO: Deconflict w/ definition in interval.coffee
module.exports.In = class In extends Expression
  constructor: (json) ->
    super

  exec: (ctx) ->
    [item, container] = @execArgs(ctx)

    switch
      when typeIsArray container
        return item in container
      when container instanceof ValueSet
        return container.hasCode item

# TODO: Deconflict w/ definition in interval.coffee
module.exports.Contains = class Contains extends UnimplementedExpression

# TODO: Deconflict w/ definition in interval.coffee
module.exports.Includes = class Includes extends Expression
  constructor: (json) ->
    super

  exec: (ctx) ->
    args = @execArgs(ctx)
    args[0].includes args[1]

# TODO: Deconflict w/ definition in interval.coffee
module.exports.IncludedIn = class IncludedIn extends UnimplementedExpression

# TODO: Deconflict w/ definition in interval.coffee
module.exports.ProperIncludes = class ProperIncludes extends UnimplementedExpression

# TODO: Deconflict w/ definition in interval.coffee
module.exports.ProperIncludedIn = class ProperIncludedIn extends UnimplementedExpression

module.exports.Sort = class Sort
  constructor:(json) ->
    @by = build json?.by

  sort: (values) ->
    self = @
    if @by
      values.sort (a,b) ->
        order = 0
        for item in self.by
          order = item.exec(a,b)
          if order != 0 then break
        order

module.exports.ForEach = class ForEach extends UnimplementedExpression

module.exports.Expand = class Expand extends UnimplementedExpression

module.exports.Distinct = class Distinct extends Expression
  constructor: (json) ->
    super
    @source = build json.source

  exec: (ctx) ->
    container = {}
    container[itm] = itm for itm in @source.exec(ctx)
    value for key, value of container

module.exports.Current = class Current extends UnimplementedExpression

# TODO: Not in the ELM Spec
module.exports.First = class First extends UnimplementedExpression

# TODO: Not in the ELM Spec
module.exports.Last = class Last extends UnimplementedExpression
