package br.edu.ifpb.dac.falacampus.business.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import br.edu.ifpb.dac.falacampus.model.entity.Answer;
import br.edu.ifpb.dac.falacampus.model.entity.Comment;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.enums.CommentType;
import br.edu.ifpb.dac.falacampus.model.enums.StatusComment;
import br.edu.ifpb.dac.falacampus.model.repository.CommentRepository;
import br.edu.ifpb.dac.falacampus.presentation.dto.DetailsCommentDto;

class CommentServiceTest {
	
	@InjectMocks
	private CommentService commentService;
	
	@Mock
	private CommentRepository commentRepository;
	
	@Mock
	private ModelMapper mapper;
	
	private static DetailsCommentDto commentDto;
	
	private static Optional<Comment> optionalComment;
	
	@Spy
	private List<Comment> listComment;
	
	private static Comment comment;
	
	
	@BeforeAll
	static void initAll() {
		comment = new Comment(1L, "Fechadura Digital", "A fechadura não funciona", CommentType.REVIEW, StatusComment.NOT_SOLVED, new User(), new Departament(),new Answer(), new File("Doc"));
		commentDto = new DetailsCommentDto(comment);
		optionalComment = Optional.of(new Comment(1L, "Fechadura Digital", "A fechadura não funciona", CommentType.REVIEW, StatusComment.NOT_SOLVED, new User(), new Departament(),new Answer(), new File("Doc")));		
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	@DisplayName("SaveComment")
	@Test
	public void saveCommentTest() {
		
		when(commentRepository.save(comment))
		.thenReturn(comment);
		
		Comment returnSave = commentService.save(comment);
		
		assertNotNull(returnSave);
		assertEquals(Comment.class, returnSave.getClass());
		
		assertEquals(comment.getId(), returnSave.getId());
		assertEquals(comment.getTitle(), returnSave.getTitle());
		assertEquals(comment.getMessage(), returnSave.getMessage());
		assertEquals(comment.getCommentType(), returnSave.getCommentType());
		assertEquals(comment.getStatusComment(), returnSave.getStatusComment());
		assertEquals(comment.getAuthor(), returnSave.getAuthor());
		assertEquals(comment.getDepartament(), returnSave.getDepartament());
		assertEquals(comment.getAnswer(), returnSave.getAnswer());
		assertEquals(comment.getAttachment(), returnSave.getAttachment());
	
		verify(commentRepository, times(1)).save(returnSave);
		
	}
		
	@DisplayName("SaveCommentDto")
	@Test
	public void saveCommentDtoTest() {
		
		commentService.save(comment);
		Comment c = mapper.map(commentDto, Comment.class);
		
		verify(commentRepository, times(1)).save(c);
	}
	
	@DisplayName("FindById")
	@Test
	public void findByIdTest() {
		
		when(commentRepository.findById(comment.getId())).thenReturn(optionalComment);
		commentService.findById(comment.getId());
		
		verify(commentRepository, times(1)).findById(comment.getId());
	
	}

	@DisplayName("FindAllComment")
	@Test
	public void findAllTest() {	
		when(commentRepository.findAll())
		.thenReturn(listComment);
		 
		verify(commentRepository, times(0)).findAll();
		
	}
	
	
	@Test
	void spyTestList() {
		listComment = new ArrayList<Comment>();
		
		assertEquals(true, listComment.isEmpty());
		
		assertNotNull(comment);	
		listComment.add(comment);
		
		
		
			
		assertEquals(1, listComment.size());
//		
//		when(listComment.size()).thenReturn(100);		
//	    assertEquals(100, listComment.size());
		
	}
	
	@Test
	void testDeleteById() {		
		assertThrows(IllegalStateException.class, 
				() -> commentService.deleteById(null));
	}

	@Test
	void testFindById () { 
		assertThrows(IllegalStateException.class,
				() -> commentService.findById(null));
	}
}