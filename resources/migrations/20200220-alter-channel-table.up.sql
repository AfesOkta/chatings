Alter TABLE public.channels
add CONSTRAINT fk_pointer FOREIGN KEY (list)
      REFERENCES public.pointer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE;
