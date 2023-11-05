testbench_module = top

bsp:
	./mill -i mill.bsp.BSP/install

idea:
	mill mill.scalalib.GenIdea/idea

test:
	sbt test

wave:
	cd test_run_dir/$(testbench_module)_should_pass && gtkwave $(testbench_module).vcd

sim: test wave

