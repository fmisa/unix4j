package org.unix4j.unix;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.unix4j.Unix4j;
import org.unix4j.util.MultilineString;

public class SortTest {

	static {
		Locale.setDefault(Locale.US);//make sure that sort is always the same, uses Collator.getInstance() with default locale
	}

	@Test
	public void testSortSimple() {
		final MultilineString input = new MultilineString();
		input
				.appendLine("To be or not to be")
				.appendLine("That is the question")
				.appendLine("Whether tis nobler in the mind")
				.appendLine("To suffer the")
				.appendLine("10 slings and arrows")
				.appendLine("of outrageous fortune")
				.appendLine("or to bear")
				.appendLine("arms against a sea of troubles.");

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
				.appendLine("10 slings and arrows")
				.appendLine("arms against a sea of troubles.")
				.appendLine("of outrageous fortune")
				.appendLine("or to bear")
				.appendLine("That is the question")
				.appendLine("To be or not to be")
				.appendLine("To suffer the")
				.appendLine("Whether tis nobler in the mind");

		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort().toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.unique).toStringResult());
	}

	@Test
	public void testSortReverse() {
		final MultilineString input = new MultilineString();
		input
				.appendLine("To be or not to be")
				.appendLine("That is the question")
				.appendLine("Whether tis nobler in the mind")
				.appendLine("To suffer the")
				.appendLine("10 slings and arrows")
				.appendLine("of outrageous fortune")
				.appendLine("or to bear")
				.appendLine("arms against a sea of troubles.");

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
				.appendLine("Whether tis nobler in the mind")
				.appendLine("To suffer the")
				.appendLine("To be or not to be")
				.appendLine("That is the question")
				.appendLine("or to bear")
				.appendLine("of outrageous fortune")
				.appendLine("arms against a sea of troubles.")
				.appendLine("10 slings and arrows");

		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.reverse).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.r).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.reverse.unique).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.u.r).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-r").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-ur").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("--reverse").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("--unique", "--reverse").toStringResult());
	}

	@Test
	public void testSortWithDuplicates() {
		final MultilineString input = new MultilineString();
		input
				.appendLine("To be or not to BE")
				.appendLine("That is the question")
				.appendLine("To be or not to be")
				.appendLine("To suffer the")
				.appendLine("10 slings and arrows")
				.appendLine("10 slings and ARROWS")
				.appendLine("or to bear")
				.appendLine("10 slings and arrows")
		;

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
				.appendLine("10 slings and arrows")
				.appendLine("10 slings and arrows")
				.appendLine("10 slings and ARROWS")
				.appendLine("or to bear")
				.appendLine("That is the question")
				.appendLine("To be or not to be")
				.appendLine("To be or not to BE")
				.appendLine("To suffer the")
		;

		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort().toStringResult());
	}

	@Test
	public void testSortWithDuplicatesIgnoreCase() {
		final MultilineString input = new MultilineString();
		input
			.appendLine("To be or not to BE")
			.appendLine("That is the question")
			.appendLine("To be or not to be")
			.appendLine("To suffer the")
			.appendLine("10 slings and arrows")
			.appendLine("10 slings and ARROWS")
			.appendLine("or to bear")
			.appendLine("10 slings and arrows")
		;

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
				.appendLine("10 slings and arrows")
				.appendLine("10 slings and ARROWS")
				.appendLine("10 slings and arrows")
				.appendLine("or to bear")
				.appendLine("That is the question")
				.appendLine("To be or not to BE")
				.appendLine("To be or not to be")
				.appendLine("To suffer the")
		;

		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.ignoreCase).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-f").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("--ignoreCase").toStringResult());
	}

	@Test
	public void testSortWithDuplicateUniques() {
		final MultilineString input = new MultilineString();
		input
				.appendLine("To be or not to BE")
				.appendLine("That is the question")
				.appendLine("To be or not to be")
				.appendLine("To suffer the")
				.appendLine("10 slings and arrows")
				.appendLine("10 slings and ARROWS")
				.appendLine("or to bear")
				.appendLine("10 slings and arrows")
		;

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
				.appendLine("10 slings and arrows")
				.appendLine("10 slings and ARROWS")
				.appendLine("or to bear")
				.appendLine("That is the question")
				.appendLine("To be or not to be")
				.appendLine("To be or not to BE")
				.appendLine("To suffer the")
		;

		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.unique).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-u").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("--unique").toStringResult());
	}

	@Test
	public void testSortWithDuplicatesIgnoreCaseUnique() {
		final MultilineString input = new MultilineString();
		input
			.appendLine("To be or not to BE")
			.appendLine("That is the question")
			.appendLine("To be or not to be")
			.appendLine("To suffer the")
			.appendLine("10 slings and arrows")
			.appendLine("10 slings and ARROWS")
			.appendLine("or to bear")
			.appendLine("10 slings and arrows")
		;

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
				.appendLine("10 slings and arrows")
				.appendLine("or to bear")
				.appendLine("That is the question")
				.appendLine("To be or not to BE")
				.appendLine("To suffer the")
		;

		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.ignoreCase.unique).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-fu").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-uf").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("--ignoreCase", "--unique").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("--unique", "--ignoreCase").toStringResult());
	}

	@Test
	public void testSortIgnoreBlanks() {
		final MultilineString input = new MultilineString();
		input
			.appendLine("   Abc")
			.appendLine("  Abc")
			.appendLine(" Abc")
			.appendLine("Abc")
			.appendLine("AAA")
			.appendLine(" AAb")
			.appendLine("  Abz")
			.appendLine("   ZZZ")
			.appendLine("\t\tZZZ")
		;

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
			.appendLine("AAA")
			.appendLine(" AAb")
			.appendLine("   Abc")
			.appendLine("  Abc")
			.appendLine(" Abc")
			.appendLine("Abc")
			.appendLine("  Abz")
			.appendLine("   ZZZ")
			.appendLine("\t\tZZZ")
		;
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.ignoreLeadingBlanks).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("--ignoreLeadingBlanks").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-b").toStringResult());
	}

	@Test
	public void testSortUniqueIgnoreBlanks() {
		final MultilineString input = new MultilineString();
		input
			.appendLine("   Abc")
			.appendLine("  Abc")
			.appendLine(" Abc")
			.appendLine("Abc")
			.appendLine("AAA")
			.appendLine(" AAb")
			.appendLine("  Abz")
			.appendLine("   ZZZ")
			.appendLine("\t\tZZZ")
		;

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
			.appendLine("AAA")
			.appendLine(" AAb")
			.appendLine("   Abc")
			.appendLine("  Abz")
			.appendLine("   ZZZ")
		;
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.unique.ignoreLeadingBlanks).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-ub").toStringResult());
	}

	@Test
	public void testSortThenReverse() {
		final MultilineString input = new MultilineString();
		input
				.appendLine("To be or not to be")
				.appendLine("That is the question")
				.appendLine("Whether tis nobler in the mind")
				.appendLine("To suffer the")
				.appendLine("10 slings and arrows")
				.appendLine("of outrageous fortune")
				.appendLine("or to bear")
				.appendLine("arms against a sea of troubles.");

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
				.appendLine("Whether tis nobler in the mind")
				.appendLine("To suffer the")
				.appendLine("To be or not to be")
				.appendLine("That is the question")
				.appendLine("or to bear")
				.appendLine("of outrageous fortune")
				.appendLine("arms against a sea of troubles.")
				.appendLine("10 slings and arrows");

		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort().sort(Sort.Options.reverse).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.unique).sort(Sort.Options.reverse.unique).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort().sort(Sort.Options.reverse.unique).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.unique).sort(Sort.Options.reverse).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort().sort("-r").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-u").sort("-r").toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-u").sort("-ru").toStringResult());
	}

	@Test
	public void testSortOneLine() {
		assertEquals("blah", Unix4j.fromString("blah").sort().toStringResult());
		assertEquals("blah", Unix4j.fromString("blah").sort(Sort.Options.reverse).toStringResult());
		assertEquals("blah", Unix4j.fromString("blah").sort(Sort.Options.unique).toStringResult());
		assertEquals("blah", Unix4j.fromString("blah").sort(Sort.Options.unique.reverse).toStringResult());
		assertEquals("blah", Unix4j.fromString("blah").sort("-ur").toStringResult());
		assertEquals("blah", Unix4j.fromString("blah").sort("--unique").toStringResult());
		assertEquals("blah", Unix4j.fromString("blah").sort("--reverse").toStringResult());
		assertEquals("blah", Unix4j.fromString("blah").sort("--reverse", "--unique").toStringResult());
	}

	@Test
	public void testSortNothing() {
		assertEquals("", Unix4j.fromString("").sort().toStringResult());
		assertEquals("", Unix4j.fromString("").sort(Sort.Options.reverse).toStringResult());
		assertEquals("", Unix4j.fromString("").sort(Sort.Options.unique).toStringResult());
		assertEquals("", Unix4j.fromString("").sort(Sort.Options.unique.reverse).toStringResult());
		assertEquals("", Unix4j.fromString("").sort("-r").toStringResult());
		assertEquals("", Unix4j.fromString("").sort("-ru").toStringResult());
		assertEquals("", Unix4j.fromString("").sort("--reverse").toStringResult());
		assertEquals("", Unix4j.fromString("").sort("--unique").toStringResult());
		assertEquals("", Unix4j.fromString("").sort("--unique", "--reverse").toStringResult());
	}

	@Test
	public void testSortNumbers() {
		final MultilineString input = new MultilineString();
		input
				.appendLine("1000 To be or not to be")
				.appendLine("10 That is the question")
				.appendLine("1 Whether tis nobler in the mind")
				.appendLine("999 To suffer the")
				.appendLine("8888 10 slings and arrows")
				.appendLine("7 of outrageous fortune")
				.appendLine("6 or to bear")
				.appendLine("5 arms against a sea of troubles.");

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
				.appendLine("1000 To be or not to be")
				.appendLine("10 That is the question")
				.appendLine("1 Whether tis nobler in the mind")
				.appendLine("5 arms against a sea of troubles.")
				.appendLine("6 or to bear")
				.appendLine("7 of outrageous fortune")
				.appendLine("8888 10 slings and arrows")
				.appendLine("999 To suffer the");


		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort().toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.unique).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("--unique").toStringResult());
	}

	@Test
	public void testSortNumbersReverse() {
		final MultilineString input = new MultilineString();
		input
				.appendLine("1000 To be or not to be")
				.appendLine("10 That is the question")
				.appendLine("1 Whether tis nobler in the mind")
				.appendLine("999 To suffer the")
				.appendLine("8888 10 slings and arrows")
				.appendLine("7 of outrageous fortune")
				.appendLine("6 or to bear")
				.appendLine("5 arms against a sea of troubles.");

		final MultilineString expectedOutput = new MultilineString();
		expectedOutput
				.appendLine("999 To suffer the")
				.appendLine("8888 10 slings and arrows")
				.appendLine("7 of outrageous fortune")
				.appendLine("6 or to bear")
				.appendLine("5 arms against a sea of troubles.")
				.appendLine("1 Whether tis nobler in the mind")
				.appendLine("10 That is the question")
				.appendLine("1000 To be or not to be");

		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.reverse).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.r).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort(Sort.Options.r.u).toStringResult());
		assertEquals(expectedOutput.toString(), Unix4j.fromString(input.toString()).sort("-ru").toStringResult());
	}
}
