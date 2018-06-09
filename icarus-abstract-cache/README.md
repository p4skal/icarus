# Icarus: Abstract Cache
When  we designed the *Icarus-Framework*, we did not want to use a concrete cache for our internals.
The `icarus-abstract-cache` module is used to abstract away little details about different
caches that may be used by *Icarus* internally. It simply allows a broad range of caches
to be used as long as they can be applied to the protocol found in the module.
When wanting to make an existing cache compatible, the *Adapter* or rather *Delegate* pattern 
may be used to write an implementation of the *abstract Icarus cache*.

